package pe.edu.lab16.inscripcion.dto;

import jakarta.validation.constraints.*;

public record InscripcionRequest(
    @NotNull(message = "idEvento es obligatorio") @Positive(message = "idEvento debe ser positivo") Long idEvento,
    @NotNull(message = "idAsistente es obligatorio") @Positive(message = "idAsistente debe ser positivo") Long idAsistente) {}
