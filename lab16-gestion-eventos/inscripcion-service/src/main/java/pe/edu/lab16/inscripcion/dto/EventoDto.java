package pe.edu.lab16.inscripcion.dto;

import java.time.LocalDate;

// Copia mínima de lo que responde evento-service
public record EventoDto(Long id, String nombre, String lugar, LocalDate fecha, Integer capacidad, String estado) {}
