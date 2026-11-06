package pe.edu.lab16.asistente.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asistente", uniqueConstraints = @UniqueConstraint(name = "uk_asistente_email", columnNames = "email"))
public class Asistente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80) private String nombre;
    @Column(nullable = false, length = 80) private String apellido;
    @Column(nullable = false, length = 120) private String email;
    @Column(length = 15) private String telefono;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
