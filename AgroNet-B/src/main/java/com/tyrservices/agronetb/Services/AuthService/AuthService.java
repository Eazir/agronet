package com.tyrservices.agronetb.Services.AuthService;

import com.tyrservices.agronetb.Models.DTOs.UsuarioDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface AuthService {
    String registro(UsuarioDTO usuarioN, String confirmarContraseña, String tipo, Model model, RedirectAttributes redirectAttributes);
    String login(String username, String password, String tipo, HttpSession session, HttpServletResponse response, RedirectAttributes redirectAttributes);
}
