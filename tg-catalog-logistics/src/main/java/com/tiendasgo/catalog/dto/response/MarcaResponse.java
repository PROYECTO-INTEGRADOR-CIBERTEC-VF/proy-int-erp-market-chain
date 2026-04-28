package com.tiendasgo.catalog.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarcaResponse {
    private Integer id;
    private String nombre;
    private String codigoMarca;
    private Boolean activo;
}

