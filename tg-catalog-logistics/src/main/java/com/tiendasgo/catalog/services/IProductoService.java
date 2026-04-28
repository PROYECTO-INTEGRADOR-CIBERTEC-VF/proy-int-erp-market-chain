package com.tiendasgo.catalog.services;

import com.tiendasgo.catalog.dto.response.ProductoResponse;
import java.util.List;

public interface IProductoService {
    List<ProductoResponse> listarTodos();
}
