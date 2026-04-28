package com.tiendasgo.catalog.services.impl;

import com.tiendasgo.catalog.domain.repository.ProductoRepository;
import com.tiendasgo.catalog.dto.response.ProductoResponse;
import com.tiendasgo.catalog.services.IProductoService;
import com.tiendasgo.catalog.utils.ProductoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public List<ProductoResponse> listarTodos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
