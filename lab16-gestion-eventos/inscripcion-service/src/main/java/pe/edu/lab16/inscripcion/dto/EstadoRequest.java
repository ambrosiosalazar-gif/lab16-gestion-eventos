package pe.edu.lab16.inscripcion.dto;

import pe.edu.lab16.inscripcion.model.EstadoInscripcion;
import jakarta.validation.constraints.NotNull;

public record EstadoRequest(@NotNull(message = "El estado es obligatorio (CONFIRMADA, CANCELADA)") EstadoInscripcion estado) {}
