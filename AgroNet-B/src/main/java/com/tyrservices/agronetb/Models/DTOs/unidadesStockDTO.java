package com.tyrservices.agronetb.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class unidadesStockDTO {
    private String vendeKg;
    private String vendeLb;
    private String vendeArroba;
}
