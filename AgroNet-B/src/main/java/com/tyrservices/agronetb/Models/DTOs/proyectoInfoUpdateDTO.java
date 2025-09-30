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
public class proyectoInfoUpdateDTO {
    private String nombreProyecto;
    private String descripcion;
    private BigDecimal meta;
    private String urlImagen;
}
