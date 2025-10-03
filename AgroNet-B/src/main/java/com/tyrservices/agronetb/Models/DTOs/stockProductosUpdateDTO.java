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
public class stockProductosUpdateDTO {
    private BigDecimal cantidadKg;
    private BigDecimal cantidadUnd;
    private BigDecimal pesoArroba;
    private Boolean vendeKg;
    private Boolean vendeLb;
    private Boolean vendeUnd;
    private Boolean vendeArroba;
}
