package pe.edu.lab16.asistente.repository;

import pe.edu.lab16.asistente.model.Asistente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsistenteRepository extends JpaRepository<Asistente, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
}
