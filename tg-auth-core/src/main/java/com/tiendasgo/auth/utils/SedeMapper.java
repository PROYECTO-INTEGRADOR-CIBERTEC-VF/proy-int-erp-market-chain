package com.tiendasgo.auth.utils;

import com.tiendasgo.auth.dto.response.SedeResponse;
import com.tiendasgo.auth.domain.entity.Sede;

public final class SedeMapper {

    private SedeMapper() {
    }

    public static SedeResponse toResponse(Sede sede) {
        return new SedeResponse(
            sede.getIdSede(),
            sede.getNombre(),
            sede.getEmail(),
            sede.getGerenteNombre(),
            sede.getDireccion(),
            sede.getUbigeo(),
            sede.getTelefono(),
            sede.getEsAlmacenCentral(),
            sede.getEstado(),
            sede.getHorarioConfig()
        );
    }
}
