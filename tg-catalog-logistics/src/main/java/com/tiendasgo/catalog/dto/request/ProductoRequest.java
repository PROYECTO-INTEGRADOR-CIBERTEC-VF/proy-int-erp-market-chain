package com.tiendasgo.catalog.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoRequest {
    @NotNull
    private Integer idMarca;

    @NotNull
    private Integer idSubCategoria;

    @NotBlank
    @Size(max = 100)
    private String nombreBase;

    @Size(max = 50)
    private String variante;

    @Size(max = 10)
    private String medidaValor;

    @Size(max = 5)
    private String medidaUnidad;

    private String sku; // Ignorado en creación, generado por backend

    @NotNull
    @Digits(integer = 15, fraction = 4)
    private BigDecimal precioCosto;

    @NotNull
    @Digits(integer = 15, fraction = 4)
    private BigDecimal precioVenta;

    @Size(max = 255)
    private String imagenUrl;

    @NotNull
    private Boolean estado;
}
