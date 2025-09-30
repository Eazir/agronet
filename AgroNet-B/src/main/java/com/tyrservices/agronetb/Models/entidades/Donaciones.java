package com.tyrservices.agronetb.Models.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "donaciones")
public class Donaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_donacion")
    private int codigoDonacion;

    @JoinColumn(name = "codigo_proyecto", nullable = false)
    private Integer codigoProyecto;

    @JoinColumn(name = "doc_consumidor", nullable = false)
    private Integer docConsumidor;

    @Column(name = "fecha_donacion", updatable = false)
    private LocalDateTime fechaDonacion;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
}