package com.tiendasgo.catalog.controllers;

import com.tiendasgo.catalog.dto.request.MarcaRequest;
import com.tiendasgo.catalog.dto.response.MarcaResponse;
import com.tiendasgo.catalog.services.IMarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/catalog/marcas")
@RequiredArgsConstructor
@Validated
public class MarcaController {

    private final IMarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<MarcaResponse>> listar() {
        return ResponseEntity.ok(marcaService.listarTodos());
    }

    @GetMapping("/generar-codigo")
    public ResponseEntity<String> generarCodigo(@RequestParam("nombre") String nombre) {
        String codigo = marcaService.generarCodigoPorNombre(nombre);
        return ResponseEntity.ok(codigo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(marcaService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarcaResponse> crear(@Valid @RequestBody MarcaRequest req) {
        MarcaResponse created = marcaService.crear(req);
        return ResponseEntity.created(URI.create("/api/catalog/marcas/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarcaResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody MarcaRequest req) {
        return ResponseEntity.ok(marcaService.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        marcaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
