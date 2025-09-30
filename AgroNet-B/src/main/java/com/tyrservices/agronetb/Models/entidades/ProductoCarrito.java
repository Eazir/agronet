package com.tyrservices.agronetb.Models.entidades;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Formula;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "productos_carrito")
public class ProductoCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_producto_carrito")
    private Integer codigoProductoCarrito;

    @JoinColumn(name = "codigo_carrito", nullable = false)
    private Integer codigoCarrito;

    @ManyToOne
    @JoinColumn(name = "codigo_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "unidad_venta", nullable = false)
    private String unidadVenta;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2 )
    private BigDecimal subtotal;
}

