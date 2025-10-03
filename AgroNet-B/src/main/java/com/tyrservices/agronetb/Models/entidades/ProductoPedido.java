package com.tyrservices.agronetb.Models.entidades;

import java.math.BigDecimal;
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
@Table(name = "productos_pedido")
public class ProductoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_producto_pedido")
    private Long codigoProductoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_pedido", nullable = false)
    private Pedido codigoPedido;

    @ManyToOne(fetch = FetchType.EAGER)
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
