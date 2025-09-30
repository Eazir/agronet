package com.tyrservices.agronetb.Models.entidades;

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
@Table(name = "usuario_productor")
public class UsuarioProductor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_productor")
    private Integer docProductor;

    @JoinColumn(name = "codigo_doc", nullable = false)
    private Integer codigoDoc;

    @Column(name = "nombre_usuario", nullable = false, length = 100)
    private String nombreUsuario;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "contraseña", nullable = false, length = 255)
    private String contraseña;
}