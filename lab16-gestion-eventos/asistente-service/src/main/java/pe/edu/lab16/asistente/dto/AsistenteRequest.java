package pe.edu.lab16.asistente.dto;

import jakarta.validation.constraints.*;

public record AsistenteRequest(
    @NotBlank(message = "El nombre es obligatorio") @Size(max = 80) String nombre,
    @NotBlank(message = "El apellido es obligatorio") @Size(max = 80) String apellido,
    @NotBlank(message = "El email es obligatorio") @Email(message = "Email con formato inválido") String email,
    @Pattern(regexp = "^[0-9]{9,15}$", message = "El teléfono debe tener entre 9 y 15 dígitos") String telefono) {}
