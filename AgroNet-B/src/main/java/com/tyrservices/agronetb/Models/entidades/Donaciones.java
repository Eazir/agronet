package com.tyrservices.agronetb.Models.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "donaciones")
public class Donaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_donacion")
    private Long codigoDonacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_proyecto", nullable = false)
    private Proyecto codigoProyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_consumidor", nullable = false)
    private UsuarioConsumidor docConsumidor;

    @Column(name = "fecha_donacion", updatable = false)
    @CreatedDate
    private LocalDateTime fechaDonacion;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
}