package pe.edu.lab16.inscripcion.service;

import pe.edu.lab16.inscripcion.client.*;
import pe.edu.lab16.inscripcion.dto.*;
import pe.edu.lab16.inscripcion.exception.*;
import pe.edu.lab16.inscripcion.model.*;
import pe.edu.lab16.inscripcion.repository.InscripcionRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InscripcionService {
    private final InscripcionRepository repo;
    private final EventoClient eventoClient;
    private final AsistenteClient asistenteClient;

    public InscripcionService(InscripcionRepository repo, EventoClient eventoClient, AsistenteClient asistenteClient) {
        this.repo = repo; this.eventoClient = eventoClient; this.asistenteClient = asistenteClient;
    }

    @Transactional(readOnly = true)
    public List<InscripcionResponse> listar() { return repo.findAll().stream().map(this::toDto).toList(); }

    @Transactional(readOnly = true)
    public List<InscripcionResponse> listarPorEvento(Long idEvento) { return repo.findByIdEvento(idEvento).stream().map(this::toDto).toList(); }

    @Transactional(readOnly = true)
    public InscripcionResponse obtener(Long id) { return toDto(buscar(id)); }

    @Transactional
    public InscripcionResponse crear(InscripcionRequest r) {
        EventoDto ev = eventoClient.obtener(r.idEvento());          // REST -> evento-service (404 si no existe)
        asistenteClient.validarExiste(r.idAsistente());              // REST -> asistente-service (404 si no existe)
        if (!"ACTIVO".equals(ev.estado())) throw new BusinessException("El evento no está ACTIVO (estado: " + ev.estado() + ")");
        if (repo.existsByIdEventoAndIdAsistenteAndEstado(r.idEvento(), r.idAsistente(), EstadoInscripcion.CONFIRMADA))
            throw new BusinessException("El asistente ya está inscrito en este evento");
        validarCupo(r.idEvento(), ev.capacidad());
        Inscripcion i = new Inscripcion();
        i.setIdEvento(r.idEvento()); i.setIdAsistente(r.idAsistente());
        i.setFecha(LocalDate.now()); i.setEstado(EstadoInscripcion.CONFIRMADA);
        return toDto(repo.save(i));
    }

    @Transactional
    public InscripcionResponse cambiarEstado(Long id, EstadoRequest r) {
        Inscripcion i = buscar(id);
        if (r.estado() == EstadoInscripcion.CONFIRMADA && i.getEstado() != EstadoInscripcion.CONFIRMADA) {
            validarCupo(i.getIdEvento(), eventoClient.obtener(i.getIdEvento()).capacidad());
        }
        i.setEstado(r.estado());
        return toDto(repo.save(i));
    }

    @Transactional
    public void eliminar(Long id) { repo.delete(buscar(id)); }

    private void validarCupo(Long idEvento, Integer capacidad) {
        if (repo.countByIdEventoAndEstado(idEvento, EstadoInscripcion.CONFIRMADA) >= capacidad)
            throw new BusinessException("Capacidad del evento agotada (" + capacidad + ")");
    }
    private Inscripcion buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id " + id));
    }
    private InscripcionResponse toDto(Inscripcion i) {
        return new InscripcionResponse(i.getId(), i.getIdEvento(), i.getIdAsistente(), i.getFecha(), i.getEstado());
    }
}
