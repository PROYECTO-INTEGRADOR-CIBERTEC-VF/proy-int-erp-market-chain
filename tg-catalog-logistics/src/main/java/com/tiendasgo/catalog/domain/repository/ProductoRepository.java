package com.tiendasgo.catalog.domain.repository;

import com.tiendasgo.catalog.domain.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByEstado(Boolean estado);
    List<Producto> findByMarcaId(Integer marcaId);
    List<Producto> findBySubCategoriaId(Integer subCategoriaId);
}
