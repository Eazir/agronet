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
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_proyecto")
    private Long codigoProyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_productor", nullable = false)
    private UsuarioProductor docProductor;

    @Column(name = "nombre_proyecto", nullable = false, length = 100)
    private String nombreProyecto;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "meta", nullable = false, precision = 10, scale = 2)
    private BigDecimal meta;

    @Column(name = "recaudado", precision = 10, scale = 2)
    private BigDecimal recaudado;

    @Column(name = "donaciones_recibidas")
    private Integer donacionesRecibidas;

    @Column(name = "url_imagen", length = 1000)
    private String urlImagen;

    @Column(name = "fecha_creacion", updatable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    @LastModifiedDate
    private LocalDateTime fechaActualizacion;

}