package pe.edu.lab16.inscripcion.controller;

import pe.edu.lab16.inscripcion.dto.*;
import pe.edu.lab16.inscripcion.service.InscripcionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {
    private final InscripcionService service;
    public InscripcionController(InscripcionService service) { this.service = service; }

    @GetMapping public List<InscripcionResponse> listar() { return service.listar(); }
    @GetMapping("/{id}") public InscripcionResponse obtener(@PathVariable Long id) { return service.obtener(id); }
    @GetMapping("/evento/{idEvento}") public List<InscripcionResponse> porEvento(@PathVariable Long idEvento) { return service.listarPorEvento(idEvento); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public InscripcionResponse crear(@Valid @RequestBody InscripcionRequest r) { return service.crear(r); }
    @PutMapping("/{id}/estado")
    public InscripcionResponse cambiarEstado(@PathVariable Long id, @Valid @RequestBody EstadoRequest r) { return service.cambiarEstado(id, r); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
