package com.tyrservices.agronetb.Services.UsersService;

import com.tyrservices.agronetb.Models.entidades.UsuarioConsumidor;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;

public interface UsersService {
    boolean actualizarProductor(UsuarioProductor usuarioAct);
    boolean cambiarContraseñaP(Long userId, String currentPassword, String newPassword);
    boolean actualizarConsumidor(UsuarioConsumidor usurioAct);
    boolean cambiarContraseñaC(Long userId, String currentPassword, String newPassword);
}
