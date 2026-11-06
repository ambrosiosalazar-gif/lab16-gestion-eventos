package pe.edu.lab16.evento.service;

import pe.edu.lab16.evento.dto.*;
import pe.edu.lab16.evento.exception.ResourceNotFoundException;
import pe.edu.lab16.evento.model.Evento;
import pe.edu.lab16.evento.repository.EventoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventoService {
    private final EventoRepository repo;
    public EventoService(EventoRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public List<EventoResponse> listar() { return repo.findAll().stream().map(this::toDto).toList(); }

    @Transactional(readOnly = true)
    public EventoResponse obtener(Long id) { return toDto(buscar(id)); }

    @Transactional
    public EventoResponse crear(EventoRequest r) { return toDto(repo.save(apply(new Evento(), r))); }

    @Transactional
    public EventoResponse actualizar(Long id, EventoRequest r) { return toDto(repo.save(apply(buscar(id), r))); }

    @Transactional
    public void eliminar(Long id) { repo.delete(buscar(id)); }

    private Evento buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id " + id));
    }
    private Evento apply(Evento e, EventoRequest r) {
        e.setNombre(r.nombre()); e.setLugar(r.lugar()); e.setFecha(r.fecha());
        e.setCapacidad(r.capacidad()); e.setEstado(r.estado());
        return e;
    }
    private EventoResponse toDto(Evento e) {
        return new EventoResponse(e.getId(), e.getNombre(), e.getLugar(), e.getFecha(), e.getCapacidad(), e.getEstado());
    }
}
