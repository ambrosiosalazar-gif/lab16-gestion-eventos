package pe.edu.lab16.inscripcion.repository;

import pe.edu.lab16.inscripcion.model.EstadoInscripcion;
import pe.edu.lab16.inscripcion.model.Inscripcion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    long countByIdEventoAndEstado(Long idEvento, EstadoInscripcion estado);
    boolean existsByIdEventoAndIdAsistenteAndEstado(Long idEvento, Long idAsistente, EstadoInscripcion estado);
    List<Inscripcion> findByIdEvento(Long idEvento);
}
