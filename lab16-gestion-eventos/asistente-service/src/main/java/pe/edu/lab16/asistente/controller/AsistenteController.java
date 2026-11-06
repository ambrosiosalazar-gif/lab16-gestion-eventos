package pe.edu.lab16.asistente.controller;

import pe.edu.lab16.asistente.dto.*;
import pe.edu.lab16.asistente.service.AsistenteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asistentes")
public class AsistenteController {
    private final AsistenteService service;
    public AsistenteController(AsistenteService service) { this.service = service; }

    @GetMapping public List<AsistenteResponse> listar() { return service.listar(); }
    @GetMapping("/{id}") public AsistenteResponse obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public AsistenteResponse crear(@Valid @RequestBody AsistenteRequest r) { return service.crear(r); }
    @PutMapping("/{id}")
    public AsistenteResponse actualizar(@PathVariable Long id, @Valid @RequestBody AsistenteRequest r) { return service.actualizar(id, r); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
