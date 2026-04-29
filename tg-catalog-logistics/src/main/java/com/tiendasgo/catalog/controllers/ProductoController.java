package com.tiendasgo.catalog.controllers;

import com.tiendasgo.catalog.dto.request.ProductoRequest;
import com.tiendasgo.catalog.dto.response.ProductoResponse;
import com.tiendasgo.catalog.services.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/catalog/productos")
@CrossOrigin
@RequiredArgsConstructor
public class ProductoController {
    private final IProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarTodos() {
        List<ProductoResponse> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest req) {
        ProductoResponse created = productoService.crear(req);
        return ResponseEntity.created(URI.create("/api/catalog/productos/" + created.getId())).body(created);
    }
}
