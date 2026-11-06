package pe.edu.lab16.inscripcion.model;

import jakarta.persistence.*;
import java.time.LocalDate;

// Solo guarda los IDs: evento y asistente viven en OTRAS bases de datos (no hay FK entre BDs).
@Entity
@Table(name = "inscripcion")
public class Inscripcion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_evento", nullable = false) private Long idEvento;
    @Column(name = "id_asistente", nullable = false) private Long idAsistente;
    @Column(nullable = false) private LocalDate fecha;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private EstadoInscripcion estado;

    public Long getId() { return id; }
    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long idEvento) { this.idEvento = idEvento; }
    public Long getIdAsistente() { return idAsistente; }
    public void setIdAsistente(Long idAsistente) { this.idAsistente = idAsistente; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public EstadoInscripcion getEstado() { return estado; }
    public void setEstado(EstadoInscripcion estado) { this.estado = estado; }
}
