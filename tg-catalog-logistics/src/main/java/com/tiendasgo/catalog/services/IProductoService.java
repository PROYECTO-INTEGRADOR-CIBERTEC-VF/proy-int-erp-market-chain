package com.tiendasgo.catalog.services;

import com.tiendasgo.catalog.dto.request.ProductoRequest;
import com.tiendasgo.catalog.dto.response.ProductoResponse;
import java.util.List;

public interface IProductoService {
    List<ProductoResponse> listarTodos();
    ProductoResponse obtenerPorId(Integer id);
    ProductoResponse crear(ProductoRequest req);
    ProductoResponse actualizar(Integer id, ProductoRequest req);
    void cambiarEstado(Integer id, Boolean nuevoEstado);
}
