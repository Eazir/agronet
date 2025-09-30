package com.tyrservices.agronetb.Models.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "categoria_productos")
public class CategoriaProducto {
    @Id
    @Column(name = "codigo_categoria")
    private Integer codigoCategoria;

    @Column(name = "nombre_categoria", nullable = false, length = 100)
    private String nombreCategoria;

    @Column(name = "url_imagen", length = 1000)
    private String urlImagen;
}