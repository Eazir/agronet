package com.tyrservices.agronetb.Models.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "stock_producto")
public class StockProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private Long idStock;

    @JoinColumn(name = "codigo_producto")
    private Long codigoProducto;

    @Column(name = "cantidad_stock", precision = 10, scale = 2)
    private BigDecimal cantidadKg;

    @Column(name = "vende_kg")
    private Boolean vendeKg;

    @Column(name = "vende_lb")
    private Boolean vendeLb;

    @Column(name = "vende_tonelada")
    private Boolean vendeTonelada;
}
