package com.tiendasgo.catalog.utils;

import com.tiendasgo.catalog.domain.entity.Producto;
import com.tiendasgo.catalog.dto.response.ProductoResponse;
import com.tiendasgo.catalog.dto.request.ProductoRequest;
import com.tiendasgo.catalog.domain.entity.Marca;
import com.tiendasgo.catalog.domain.entity.SubCategoria;
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

    public Producto toEntity(ProductoRequest req, Marca marca, SubCategoria subCategoria) {
        if (req == null || marca == null || subCategoria == null) return null;
        return Producto.builder()
                .marca(marca)
                .subCategoria(subCategoria)
                .nombreBase(req.getNombreBase())
                .variante(req.getVariante())
                .medidaValor(req.getMedidaValor())
                .medidaUnidad(req.getMedidaUnidad())
                .sku(req.getSku())
                .precioCosto(req.getPrecioCosto())
                .precioVenta(req.getPrecioVenta())
                .imagenUrl(req.getImagenUrl())
                .estado(true)
                .build();
    }
}
