package pe.edu.lab16.asistente.service;

import pe.edu.lab16.asistente.dto.*;
import pe.edu.lab16.asistente.exception.*;
import pe.edu.lab16.asistente.model.Asistente;
import pe.edu.lab16.asistente.repository.AsistenteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsistenteService {
    private final AsistenteRepository repo;
    public AsistenteService(AsistenteRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public List<AsistenteResponse> listar() { return repo.findAll().stream().map(this::toDto).toList(); }

    @Transactional(readOnly = true)
    public AsistenteResponse obtener(Long id) { return toDto(buscar(id)); }

    @Transactional
    public AsistenteResponse crear(AsistenteRequest r) {
        if (repo.existsByEmail(r.email())) throw new BusinessException("Ya existe un asistente con el email " + r.email());
        return toDto(repo.save(apply(new Asistente(), r)));
    }

    @Transactional
    public AsistenteResponse actualizar(Long id, AsistenteRequest r) {
        Asistente a = buscar(id);
        if (repo.existsByEmailAndIdNot(r.email(), id)) throw new BusinessException("El email " + r.email() + " pertenece a otro asistente");
        return toDto(repo.save(apply(a, r)));
    }

    @Transactional
    public void eliminar(Long id) { repo.delete(buscar(id)); }

    private Asistente buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Asistente no encontrado con id " + id));
    }
    private Asistente apply(Asistente a, AsistenteRequest r) {
        a.setNombre(r.nombre()); a.setApellido(r.apellido()); a.setEmail(r.email()); a.setTelefono(r.telefono());
        return a;
    }
    private AsistenteResponse toDto(Asistente a) {
        return new AsistenteResponse(a.getId(), a.getNombre(), a.getApellido(), a.getEmail(), a.getTelefono());
    }
}
