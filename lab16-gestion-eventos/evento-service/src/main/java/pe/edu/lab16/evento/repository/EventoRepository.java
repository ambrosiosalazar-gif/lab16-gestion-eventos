package pe.edu.lab16.evento.repository;

import pe.edu.lab16.evento.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {}
