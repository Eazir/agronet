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
    @Column(name = "doc_productor", nullable = false)
    private Long docProductor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_doc", nullable = false)
    private TipoDocumento codigoDoc;

    @Column(name = "tipo_usuario", nullable = false, length = 100)
    private String tipoUsuario;

    @Column(name = "nombre_usuario", nullable = false, length = 100)
    private String nombreUsuario;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "contraseña", nullable = false, length = 100)
    private String contraseña;
}