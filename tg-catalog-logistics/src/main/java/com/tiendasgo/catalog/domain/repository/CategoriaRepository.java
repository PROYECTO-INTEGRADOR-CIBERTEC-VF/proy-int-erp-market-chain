package com.tiendasgo.catalog.domain.repository;

import com.tiendasgo.catalog.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Optional<Categoria> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}

