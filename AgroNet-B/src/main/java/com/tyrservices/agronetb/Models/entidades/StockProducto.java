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
    private int id;

    @JoinColumn(name = "codigo_producto")
    private Integer codigoProducto;

    @Column(name = "cantidad_kg", precision = 10, scale = 2)
    private BigDecimal cantidadKg;

    @Column(name = "peso_arroba", precision = 10, scale = 2)
    private BigDecimal pesoArroba;

    @Column(name = "vende_kg")
    private Boolean vendeKg;

    @Column(name = "vende_lb")
    private Boolean vendeLb;

    @Column(name = "vende_arroba")
    private Boolean vendeArroba;
}
