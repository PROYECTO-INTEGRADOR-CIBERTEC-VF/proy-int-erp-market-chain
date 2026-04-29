package com.tiendasgo.catalog.domain.repository;

import com.tiendasgo.catalog.domain.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, Integer id);

    @Query("SELECT COALESCE(MAX(p.id), 0) FROM Producto p WHERE p.marca.id = :idMarca AND p.subCategoria.id = :idSubCategoria")
    Integer findMaxIdByMarcaAndSubCategoria(@Param("idMarca") Integer idMarca, @Param("idSubCategoria") Integer idSubCategoria);
}
