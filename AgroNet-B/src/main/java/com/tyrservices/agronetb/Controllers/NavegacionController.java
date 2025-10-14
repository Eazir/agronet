package com.tyrservices.agronetb.Controllers;

import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import com.tyrservices.agronetb.Repositorys.TipoDocumentoCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioConsumidorCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioProductorCrudRep;
import com.tyrservices.agronetb.Services.UsersService.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class NavegacionController {

    private final UsuarioConsumidorCrudRep usuarioConsumidorCrudRep;
    private final UsersService usersService;
    private final UsuarioProductorCrudRep usuarioProductorCrudRep;
    private final TipoDocumentoCrudRep tipoDocumentoCrudRep;

    public NavegacionController(UsuarioConsumidorCrudRep usuarioConsumidorCrudRep,
                                UsuarioProductorCrudRep usuarioProductorCrudRep,
                                TipoDocumentoCrudRep tipoDocumentoCrudRep,
                                UsersService usersService) {
        this.usuarioConsumidorCrudRep = usuarioConsumidorCrudRep;
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
        this.tipoDocumentoCrudRep = tipoDocumentoCrudRep;
        this.usersService = usersService;
    }

    //GETS
    @GetMapping("/AgroNet")
    public String homePage(Model model,
                           HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");
        String email = (String) session.getAttribute("userEmail");
        String userType = (String) session.getAttribute("userType");
        model.addAttribute("isLog", userId != null);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("userEmail", email);
        model.addAttribute("userType", userType);

        return "/index.html";
    }

    @GetMapping("/auth")
    public String Autenticacion() {
        return "/auth.html";
    }

    @GetMapping("/panel-control")
    public String ControlPanel(Model model,
                               HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");
        String email = (String) session.getAttribute("userEmail");
        String tipo = (String) session.getAttribute("tipo");
        model.addAttribute("isLog", userId != null);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("userEmail", email);
        model.addAttribute("tipo", tipo);

        if (tipo == null) {
                return "redirect:auth";
        }

        if (tipo.equals("1")) {
            return "Productor/panel-controlP";
        } else if (tipo.equals("2")) {
            return "Consumidor/panel-controlC";
        }else {
            return  "redirect:auth";
        }

    }

    @GetMapping("/mi-perfil")
    public String miPerfil(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");
        String email = (String) session.getAttribute("userEmail");
        String tipo = (String) session.getAttribute("tipo");
        model.addAttribute("tipo", tipo);
        model.addAttribute("isLog", userId != null);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("userEmail", email);

        try {
            if (tipo.equals("1")) {
                Optional<UsuarioProductor> productorOpt = usuarioProductorCrudRep.findById(userId);

                if (productorOpt.isPresent()) {
                    model.addAttribute("usuario", productorOpt.get());
                    model.addAttribute("tipoDocumento", productorOpt.get().getCodigoDoc().getNombreDoc());

                    return "Productor/mi-perfilP";
                }
            }
        }catch (Exception e){
            return "auth";
        }
        return "redirect:auth";
    }

    @PostMapping("/actualizar-perfil")
    public String actualizarPerfil(@ModelAttribute UsuarioProductor productor,
                                   @RequestParam(required = false) String currentPassword,
                                   @RequestParam(required = false) String newPassword,
                                   @RequestParam(required = false) String confirmPassword,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        try {
            usersService.actualizarProductor(productor);

            if (newPassword != null && !newPassword.trim().isEmpty()) {
                if (!newPassword.equals(confirmPassword)) {
                    redirectAttributes.addAttribute("error", "Las contraseñas no coinciden");
                    return "redirect:/mi-perfil";
                }

                boolean cambioExitoso = usersService.cambiarContraseñaP(userId, currentPassword, newPassword);

                if (!cambioExitoso) {
                    redirectAttributes.addAttribute("error", "Contraseña actual incorrecta");
                    return "redirect:/mi-perfil";
                }
            }

            // Actualizar datos en sesión
            session.setAttribute("userName", productor.getNombreUsuario());
            session.setAttribute("userEmail", productor.getEmail());

            redirectAttributes.addAttribute("success", true);
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
        }

        return "redirect:/mi-perfil";
    }


    @GetMapping({"/proyectos", "/productos", "/carrito", "/pedidos"})
    public String enConstruccion(Model model,
                                 HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");
        String email = (String) session.getAttribute("userEmail");
        String userType = (String) session.getAttribute("userType");
        model.addAttribute("isLog", userId != null);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("userEmail", email);
        model.addAttribute("userType", userType);

        return "/enCosntruccion.html";
    }


}
