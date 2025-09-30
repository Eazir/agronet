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
public class productoUpdateDTO {
    private Integer codigoCategoria;
    private String nombreProducto;
    private String tipoProducto;
    private String origenProducto;
    private String unidadPrecio;
    private String descripcion;
    private BigDecimal precio;
}
