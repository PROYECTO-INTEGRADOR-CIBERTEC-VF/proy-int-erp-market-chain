package com.tiendasgo.catalog.controllers;

import com.tiendasgo.catalog.dto.response.ProductoResponse;
import com.tiendasgo.catalog.services.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}
