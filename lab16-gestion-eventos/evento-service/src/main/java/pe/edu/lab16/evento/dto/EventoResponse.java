package pe.edu.lab16.evento.dto;

import pe.edu.lab16.evento.model.EstadoEvento;
import java.time.LocalDate;

public record EventoResponse(Long id, String nombre, String lugar, LocalDate fecha, Integer capacidad, EstadoEvento estado) {}
