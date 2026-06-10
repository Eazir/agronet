package com.tyrservices.agronetb.Controllers;

import com.tyrservices.agronetb.Models.entidades.UsuarioConsumidor;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import com.tyrservices.agronetb.Repositorys.ProductoCrudRep;
import com.tyrservices.agronetb.Repositorys.ProyectoCrudRep;
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
    private final ProductoCrudRep productoCrudRep;
    private final ProyectoCrudRep proyectoCrudRep;

    public NavegacionController(UsuarioConsumidorCrudRep usuarioConsumidorCrudRep,
                                UsuarioProductorCrudRep usuarioProductorCrudRep,
                                TipoDocumentoCrudRep tipoDocumentoCrudRep,
                                UsersService usersService,
                                ProductoCrudRep productoCrudRep,
                                ProyectoCrudRep proyectoCrudRep) {
        this.usuarioConsumidorCrudRep = usuarioConsumidorCrudRep;
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
        this.tipoDocumentoCrudRep = tipoDocumentoCrudRep;
        this.usersService = usersService;
        this.productoCrudRep = productoCrudRep;
        this.proyectoCrudRep = proyectoCrudRep;
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
            long misProductos = productoCrudRep.findProductosByDocProductor_DocProductor(userId).size();
            long misProyectos = 0;
            Optional<UsuarioProductor> productorOpt = usuarioProductorCrudRep.findById(userId);
            if (productorOpt.isPresent()) {
                misProyectos = proyectoCrudRep.findProyectosByDocProductor(productorOpt.get()).size();
            }
            model.addAttribute("misProductos", misProductos);
            model.addAttribute("misProyectos", misProyectos);
            return "Productor/panel-controlP";
        } else if (tipo.equals("2")) {
            long totalProductos = productoCrudRep.findByEstadoTrue().size();
            long totalProyectos = proyectoCrudRep.count();
            model.addAttribute("totalProductos", totalProductos);
            model.addAttribute("totalProyectos", totalProyectos);
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
            } else if (tipo.equals("2")) {
                Optional<UsuarioConsumidor> consumidorOpt = usuarioConsumidorCrudRep.findById(userId);

                if (consumidorOpt.isPresent()) {
                    model.addAttribute("usuario", consumidorOpt.get());
                    model.addAttribute("tipoDocumento", consumidorOpt.get().getCodigoDoc().getNombreDoc());
                    model.addAttribute("tiposDocumento", tipoDocumentoCrudRep.findAll());

                    return "Consumidor/mi-perfilC";
                }
            }
        }catch (Exception e){
            return "auth";
        }
        return "redirect:auth";
    }

    @PostMapping("/actualizar-perfil")
    public String actualizarPerfil(@ModelAttribute UsuarioProductor productor,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        try {
            usersService.actualizarProductor(productor);

            session.setAttribute("userName", productor.getNombreUsuario());
            session.setAttribute("userEmail", productor.getEmail());

            redirectAttributes.addAttribute("successDatos", true);
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorDatos", true);
        }

        return "redirect:/mi-perfil";
    }

    @PostMapping("/actualizar-perfil-consumidor")
    public String actualizarPerfilConsumidor(@ModelAttribute UsuarioConsumidor consumidor,
                                              HttpSession session,
                                              RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        try {
            consumidor.setDocConsumidor(userId);
            usersService.actualizarConsumidor(consumidor);

            session.setAttribute("userName", consumidor.getNombreUsuario());
            session.setAttribute("userEmail", consumidor.getEmail());

            redirectAttributes.addAttribute("successDatos", true);
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorDatos", true);
        }

        return "redirect:/mi-perfil";
    }

    @PostMapping("/actualizar-contrasena")
    public String actualizarContrasena(@RequestParam String currentPassword,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmPassword,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        String tipo = (String) session.getAttribute("tipo");

        if (newPassword == null || newPassword.trim().isEmpty()) {
            redirectAttributes.addAttribute("errorPass", "La nueva contraseña no puede estar vacía");
            return "redirect:/mi-perfil";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addAttribute("errorPass", "Las contraseñas no coinciden");
            return "redirect:/mi-perfil";
        }

        if (newPassword.length() < 6) {
            redirectAttributes.addAttribute("errorPass", "La contraseña debe tener al menos 6 caracteres");
            return "redirect:/mi-perfil";
        }

        boolean cambioExitoso;
        if ("1".equals(tipo)) {
            cambioExitoso = usersService.cambiarContraseñaP(userId, currentPassword, newPassword);
        } else if ("2".equals(tipo)) {
            cambioExitoso = usersService.cambiarContraseñaC(userId, currentPassword, newPassword);
        } else {
            cambioExitoso = false;
        }

        if (!cambioExitoso) {
            redirectAttributes.addAttribute("errorPass", "Contraseña actual incorrecta");
            return "redirect:/mi-perfil";
        }

        redirectAttributes.addAttribute("successPass", true);
        return "redirect:/mi-perfil";
    }


    @GetMapping("/proyectos")
    public String proyectosPage(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth";
        }
        return "redirect:/proyectos-disponibles";
    }

    @GetMapping({"/productos", "/carrito", "/pedidos"})
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
