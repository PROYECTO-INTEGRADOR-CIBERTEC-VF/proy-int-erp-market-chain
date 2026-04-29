package com.tiendasgo.catalog.services.impl;

import com.tiendasgo.catalog.domain.entity.Producto;
import com.tiendasgo.catalog.domain.entity.Marca;
import com.tiendasgo.catalog.domain.entity.SubCategoria;
import com.tiendasgo.catalog.domain.repository.ProductoRepository;
import com.tiendasgo.catalog.domain.repository.MarcaRepository;
import com.tiendasgo.catalog.domain.repository.SubCategoriaRepository;
import com.tiendasgo.catalog.dto.request.ProductoRequest;
import com.tiendasgo.catalog.dto.response.ProductoResponse;
import com.tiendasgo.catalog.exceptions.DuplicateResourceException;
import com.tiendasgo.catalog.exceptions.InvalidPriceException;
import com.tiendasgo.catalog.exceptions.ResourceNotFoundException;
import com.tiendasgo.catalog.services.IProductoService;
import com.tiendasgo.catalog.utils.ProductoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {
    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final SubCategoriaRepository subCategoriaRepository;
    private final ProductoMapper productoMapper;

    @Override
    public List<ProductoResponse> listarTodos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoResponse crear(ProductoRequest req) {
        if (req.getPrecioVenta().compareTo(req.getPrecioCosto()) <= 0) {
            throw new InvalidPriceException("El precio de venta debe ser mayor al precio de costo.");
        }
        Marca marca = marcaRepository.findById(req.getIdMarca())
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada: " + req.getIdMarca()));
        SubCategoria subCategoria = subCategoriaRepository.findById(req.getIdSubCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoría no encontrada: " + req.getIdSubCategoria()));

        // Generar secuencial para SKU
        Integer maxId = productoRepository.findMaxIdByMarcaAndSubCategoria(marca.getId(), subCategoria.getId());
        int secuencial = (maxId != null ? maxId : 0) + 1;
        String secuencialStr = String.format("%04d", secuencial);
        String sku = subCategoria.getPrefijo() + "-" + marca.getCodigoMarca() + "-" + secuencialStr;

        if (productoRepository.existsBySku(sku)) {
            throw new DuplicateResourceException("Ya existe un producto con ese SKU: " + sku);
        }

        Producto producto = productoMapper.toEntity(req, marca, subCategoria);
        producto.setSku(sku);
        Producto saved = productoRepository.save(producto);
        return productoMapper.toResponse(saved);
    }
}
