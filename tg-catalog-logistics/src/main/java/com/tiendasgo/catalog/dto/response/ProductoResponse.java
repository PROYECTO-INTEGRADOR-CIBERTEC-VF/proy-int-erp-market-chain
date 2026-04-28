package com.tiendasgo.catalog.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoResponse {
    private Long id;
    private String nombreBase;
    private String variante;
    private String medidaValor;
    private String medidaUnidad;
    private String sku;
    private BigDecimal precioCosto;
    private BigDecimal precioVenta;
    private String imagenUrl;
    private Boolean estado;
    private String nombreMarca;
    private String nombreSubCategoria;
}

