package com.tiendasgo.catalog.domain.repository;

import com.tiendasgo.catalog.domain.entity.SubCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Integer> {

    List<SubCategoria> findByCategoriaId(Integer categoriaId);

    Optional<SubCategoria> findByNombreAndCategoriaId(String nombre, Integer categoriaId);
    boolean existsByPrefijo(String prefijo);
}

