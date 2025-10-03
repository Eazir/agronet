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
    private Long codigoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_consumidor", nullable = false)
    private UsuarioConsumidor userConsumidor;

    @Column(name = "direccion_entrega", nullable = false, length = 300)
    private String direccionEntrega;

    @Column(name = "estado", length = 50)
    private String estado = "pendiente";

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @OneToMany(mappedBy = "codigoPedido")
    private List<ProductoPedido> productos;
}