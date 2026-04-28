package com.tiendasgo.catalog.domain.repository;

import com.tiendasgo.catalog.domain.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEstado(Boolean estado);
    List<Producto> findByMarcaId(Long marcaId);
    List<Producto> findBySubCategoriaId(Long subCategoriaId);
}

