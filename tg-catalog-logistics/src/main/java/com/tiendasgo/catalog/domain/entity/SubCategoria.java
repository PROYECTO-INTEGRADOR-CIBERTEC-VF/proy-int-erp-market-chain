package com.tiendasgo.catalog.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "subcategorias", schema = "catalog")
public class SubCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategoria")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Categoria categoria;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "estado")
    private Boolean activo;

    @Column(name = "prefijo", length = 3, columnDefinition = "CHAR(3)")
    private String prefijo;
}


