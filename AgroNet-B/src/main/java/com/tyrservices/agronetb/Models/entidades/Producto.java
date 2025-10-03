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
    private Long codigoProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_categoria", nullable = false)
    private CategoriaProducto codigoCategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_productor", nullable = false)
    private UsuarioProductor docProductor;

    @Column(name = "nombre_producto", nullable = false, length = 100)
    private String nombreProducto;

    @Column(name = "origen_producto", length = 200)
    private String origenProducto;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "url_imagen", length = 1000)
    private String urlImagen;

    @Column(name = "fecha_creacion", updatable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    @LastModifiedDate
    private LocalDateTime fechaActualizacion;

}