package com.tiendasgo.catalog.domain.repository;

import com.tiendasgo.catalog.domain.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {

    Optional<Marca> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}

