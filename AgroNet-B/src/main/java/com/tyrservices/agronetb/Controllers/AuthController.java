package com.tyrservices.agronetb.Controllers;

import com.tyrservices.agronetb.Models.DTOs.UsuarioDTO;
import com.tyrservices.agronetb.Models.entidades.TipoDocumento;
import com.tyrservices.agronetb.Models.entidades.UsuarioConsumidor;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import com.tyrservices.agronetb.Repositorys.TipoDocumentoCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioConsumidorCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioProductorCrudRep;
import com.tyrservices.agronetb.Services.AuthService.AuthService;
import com.tyrservices.agronetb.Services.AuthService.AuthServiceImp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class AuthController {

    private final UsuarioConsumidorCrudRep usuarioConsumidorCrudRep;
    private final UsuarioProductorCrudRep usuarioProductorCrudRep;
    private final TipoDocumentoCrudRep tipoDocumentoCrudRep;
    private final AuthService authService;


    public AuthController(UsuarioConsumidorCrudRep usuarioConsumidorCrudRep,
                          UsuarioProductorCrudRep usuarioProductorCrudRep,
                          TipoDocumentoCrudRep tipoDocumentoCrudRep,
                          AuthService authService) {
        this.usuarioConsumidorCrudRep = usuarioConsumidorCrudRep;
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
        this.tipoDocumentoCrudRep = tipoDocumentoCrudRep;
        this.authService = authService;
    }


    //auth para productores

    @GetMapping("/login{tipo}")
    public String loginPageProductor(@RequestParam(value = "error", required = false) String error,
                                     @RequestParam(value = "success", required = false) String success,
                                     @PathVariable String tipo,
                                     Model model) {

        model.addAttribute("tipo", tipo);

        if (error != null) {
            model.addAttribute("error",
                    "Credenciales inválidas. Por favor, verifica tus datos.");
        }

        if (success != null) {
            model.addAttribute("success",
                    "session cerrada con exito");
        }
        return "login.html";
    }

    @PostMapping("/login{tipo}")
    public String processLogin(
            @RequestParam("user") String username,
            @RequestParam("password") String password,
            @PathVariable String tipo,
            HttpSession session,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {

        return authService.login(username, password, tipo, session, response, redirectAttributes);
    }

    @GetMapping("/logou")
    public String logout(HttpSession session,
                         HttpServletResponse response,
                         RedirectAttributes redirectAttributes) {
        String tipo = (String) session.getAttribute("tipo");

        session.invalidate();

        Cookie cookie = new Cookie("auth_token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        redirectAttributes.addFlashAttribute("success", "Sesión cerrada correctamente");

        if (tipo != null) {
            return "redirect:/login" + tipo;
        }
        return "redirect:/AgroNet";
    }

    @GetMapping("/registro{tipo}")
    public String registroUsuarios(Model model,
                                   @PathVariable String tipo) {
        model.addAttribute("tipoRegistro", tipo);

        // Obtener tipos de documento para el formulario
        List<TipoDocumento> tiposDocumento = tipoDocumentoCrudRep.findAll();
        model.addAttribute("tipo", tipo);
        model.addAttribute("usuarioN", new UsuarioDTO());
        model.addAttribute("tiposDocumento", tiposDocumento);

        return "registro.html";
    }

    /**
     * Procesa el formulario de registro
     */
    @PostMapping("/registro/post{tipo}")
    public String procesarRegistro(@ModelAttribute("usuarioN") UsuarioDTO usuarioN,
                                   @RequestParam("confirmarContraseña") String confirmarContraseña,
                                   @PathVariable String tipo,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        return authService.registro(usuarioN, confirmarContraseña, tipo, model, redirectAttributes);
    }


}