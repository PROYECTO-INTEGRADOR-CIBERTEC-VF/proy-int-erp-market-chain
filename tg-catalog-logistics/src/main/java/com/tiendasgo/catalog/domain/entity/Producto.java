package com.tiendasgo.catalog.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "productos", schema = "catalog")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subcategoria", nullable = false)
    private SubCategoria subCategoria;

    @Column(name = "nombre_base", length = 100, nullable = false)
    private String nombreBase;

    @Column(name = "variante", length = 50)
    private String variante;

    @Column(name = "medida_valor", length = 10)
    private String medidaValor;

    @Column(name = "medida_unidad", length = 5)
    private String medidaUnidad;

    @Column(name = "sku", length = 50, nullable = false)
    private String sku;

    @Column(name = "precio_costo", precision = 19, scale = 4, nullable = false)
    private BigDecimal precioCosto;

    @Column(name = "precio_venta", precision = 19, scale = 4, nullable = false)
    private BigDecimal precioVenta;

    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    @Column(name = "estado")
    private Boolean estado;
}
