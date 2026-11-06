package pe.edu.lab16.inscripcion.dto;

import pe.edu.lab16.inscripcion.model.EstadoInscripcion;
import java.time.LocalDate;

public record InscripcionResponse(Long id, Long idEvento, Long idAsistente, LocalDate fecha, EstadoInscripcion estado) {}
