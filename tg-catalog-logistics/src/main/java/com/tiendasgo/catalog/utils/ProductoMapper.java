package com.tiendasgo.catalog.utils;

import com.tiendasgo.catalog.domain.entity.Producto;
import com.tiendasgo.catalog.dto.response.ProductoResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public ProductoResponse toResponse(Producto producto) {
        if (producto == null) return null;
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombreBase(producto.getNombreBase())
                .variante(producto.getVariante())
                .medidaValor(producto.getMedidaValor())
                .medidaUnidad(producto.getMedidaUnidad())
                .sku(producto.getSku())
                .precioCosto(producto.getPrecioCosto())
                .precioVenta(producto.getPrecioVenta())
                .imagenUrl(producto.getImagenUrl())
                .estado(producto.getEstado())
                .nombreMarca(producto.getMarca() != null ? producto.getMarca().getNombre() : null)
                .nombreSubCategoria(producto.getSubCategoria() != null ? producto.getSubCategoria().getNombre() : null)
                .build();
    }
}
