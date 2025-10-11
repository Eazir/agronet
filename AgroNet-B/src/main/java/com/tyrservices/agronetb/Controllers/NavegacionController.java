package com.tyrservices.agronetb.Controllers;

import com.tyrservices.agronetb.Repositorys.UsuarioConsumidorCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioProductorCrudRep;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class NavegacionController {

    private final UsuarioConsumidorCrudRep usuarioConsumidorCrudRep;
    private final UsuarioProductorCrudRep usuarioProductorCrudRep;

    public NavegacionController(UsuarioConsumidorCrudRep usuarioConsumidorCrudRep,
                                UsuarioProductorCrudRep usuarioProductorCrudRep) {
        this.usuarioConsumidorCrudRep = usuarioConsumidorCrudRep;
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
    }

    //GETS
    @GetMapping({"/AgroNet{userId}", "/AgroNet"})
    public String homePage(Model model, @PathVariable(required = false) String userId) {

        if (userId != null) {
            if (usuarioConsumidorCrudRep.findById(Long.parseLong(userId)).isPresent()) {
                model.addAttribute("userName", usuarioConsumidorCrudRep.findById(Long.parseLong(userId))
                        .get().getNombreUsuario()
                );
            }

            model.addAttribute("userId", userId);
        } else {
            model.addAttribute("userName", null);
            model.addAttribute("userId", null);
        }

        model.addAttribute("tieneId", userId != null);
        return "/index.html";
    }

    @GetMapping("/auth")
    public String Autenticacion() {
        return "/auth.html";
    }

    @GetMapping({"/proyectos", "/productos", "/carrito", "/pedidos",
            "/proyectos{id}", "/productos{id}", "/carrito{id}", "/pedidos{id}",
            "/Control-Panel{id}"})
    public String enConstruccion() {
        return "/enCosntruccion.html";
    }


}
