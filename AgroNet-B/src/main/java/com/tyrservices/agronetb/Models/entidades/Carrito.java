package com.tyrservices.agronetb.Models.entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "carrito")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_carrito")
    private Long codigoCarrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_consumidor", nullable = false)
    private UsuarioConsumidor docConsumidor;

    @Column(name = "total_carrito", nullable = false,  precision = 10, scale = 2)
    private BigDecimal total_carrito;

    @Column(name = "fecha_creacion", updatable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "codigoCarrito")
    private List<ProductoCarrito> productosCarrito;

}