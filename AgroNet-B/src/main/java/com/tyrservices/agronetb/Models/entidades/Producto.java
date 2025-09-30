package com.tyrservices.agronetb.Models.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "codigo_producto")
    private Integer codigoProducto;

    @JoinColumn(name = "codigo_categoria", nullable = false)
    private Integer codigoCategoria;

    @JoinColumn(name = "doc_productor", nullable = false)
    private Integer docProductor;

    @Column(name = "nombre_producto", nullable = false, length = 100)
    private String nombreProducto;

    @Column(name = "tipo_producto", length = 100)
    private String tipoProducto;

    @Column(name = "origen_producto")
    private String origenProducto;

    @Column(name = "unidad_precio", length = 10)
    private String unidadPrecio;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "fecha_creacion", updatable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    @LastModifiedDate
    private LocalDateTime fechaActualizacion;

    @Column(name = "url_imagen", length = 1000)
    private String urlImagen;

}