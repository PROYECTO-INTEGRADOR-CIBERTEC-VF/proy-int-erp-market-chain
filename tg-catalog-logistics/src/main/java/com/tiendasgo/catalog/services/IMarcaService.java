package com.tiendasgo.catalog.services;

import com.tiendasgo.catalog.dto.request.MarcaRequest;
import com.tiendasgo.catalog.dto.response.MarcaResponse;

import java.util.List;

public interface IMarcaService {
    List<MarcaResponse> listarTodos();

    MarcaResponse obtenerPorId(Integer id);

    MarcaResponse crear(MarcaRequest req);

    String generarCodigoPorNombre(String nombre);

    MarcaResponse actualizar(Integer id, MarcaRequest req);

    void eliminar(Integer id);
}

