package com.tyrservices.agronetb.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class productosCarritoUpdateDTO {
    private Integer cantidad;
    private String unidadVenta;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
