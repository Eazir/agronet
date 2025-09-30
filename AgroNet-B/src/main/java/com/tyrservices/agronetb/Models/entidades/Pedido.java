package com.tyrservices.agronetb.Models.entidades;

import java.time.LocalDateTime;
import java.util.List;
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
@Table(name = "Pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_pedido")
    private Integer codigoPedido;

    @JoinColumn(name = "codigo_carrito", nullable = false)
    private Integer codigoCarrito;

    @Column(name = "nombre_usuario", nullable = false, length = 100)
    private String nombreUsuario;

    @Column(name = "direccion_residencia", nullable = false, length = 255)
    private String direccionResidencia;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @Column(name = "estado", length = 50)
    private String estado = "pendiente";

    @OneToMany(mappedBy = "codigoPedido")
    private List<ProductoPedido> productos;
}