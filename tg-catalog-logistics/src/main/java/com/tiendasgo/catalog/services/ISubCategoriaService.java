package com.tiendasgo.catalog.services;

import com.tiendasgo.catalog.dto.request.SubCategoriaRequest;
import com.tiendasgo.catalog.dto.response.SubCategoriaResponse;

import java.util.List;

public interface ISubCategoriaService {
    List<SubCategoriaResponse> listarTodos();

    SubCategoriaResponse obtenerPorId(Integer id);

    SubCategoriaResponse crear(SubCategoriaRequest req);

    SubCategoriaResponse actualizar(Integer id, SubCategoriaRequest req);

    void eliminar(Integer id);
}

