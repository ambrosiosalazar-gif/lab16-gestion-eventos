package pe.edu.lab16.evento.dto;

import pe.edu.lab16.evento.model.EstadoEvento;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EventoRequest(
    @NotBlank(message = "El nombre es obligatorio") @Size(max = 120) String nombre,
    @NotBlank(message = "El lugar es obligatorio") @Size(max = 150) String lugar,
    @NotNull(message = "La fecha es obligatoria") @FutureOrPresent(message = "La fecha no puede ser pasada") LocalDate fecha,
    @NotNull(message = "La capacidad es obligatoria") @Positive(message = "La capacidad debe ser mayor a 0") Integer capacidad,
    @NotNull(message = "El estado es obligatorio (ACTIVO, CANCELADO, FINALIZADO)") EstadoEvento estado) {}
