package pe.edu.lab16.evento.controller;

import pe.edu.lab16.evento.dto.*;
import pe.edu.lab16.evento.service.EventoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    private final EventoService service;
    public EventoController(EventoService service) { this.service = service; }

    @GetMapping public List<EventoResponse> listar() { return service.listar(); }
    @GetMapping("/{id}") public EventoResponse obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public EventoResponse crear(@Valid @RequestBody EventoRequest r) { return service.crear(r); }
    @PutMapping("/{id}")
    public EventoResponse actualizar(@PathVariable Long id, @Valid @RequestBody EventoRequest r) { return service.actualizar(id, r); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
