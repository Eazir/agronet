package com.tyrservices.agronetb.Models.DTOs;

import com.tyrservices.agronetb.Models.entidades.TipoDocumento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UsuarioDTO {

    private Long docUsuario;

    private TipoDocumento codigoDoc;

    private String tipoUsuario;

    private String nombreUsuario;

    private String email;

    private String contraseña;
}
