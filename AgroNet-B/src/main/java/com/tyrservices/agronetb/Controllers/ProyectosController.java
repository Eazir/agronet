package com.tyrservices.agronetb.Controllers;

import com.tyrservices.agronetb.Models.entidades.Proyecto;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import com.tyrservices.agronetb.Repositorys.UsuarioProductorCrudRep;
import com.tyrservices.agronetb.Services.ProyectosService.ProyectoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ProyectosController {

    private final ProyectoService proyectoService;
    private final UsuarioProductorCrudRep usuarioProductorCrudRep;

    public ProyectosController(ProyectoService proyectoService,
                               UsuarioProductorCrudRep usuarioProductorCrudRep) {
        this.proyectoService = proyectoService;
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
    }

    @GetMapping("/proyectos-disponibles")
    public String proyectosDisponibles(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login2";
        }

        try {
            List<Proyecto> proyectos = proyectoService.getAllProyectos();
            model.addAttribute("proyectos", proyectos);
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userId", userId);
            model.addAttribute("userName", session.getAttribute("userName"));
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar los proyectos");
        }

        return "Consumidor/proyectosC";
    }

    @GetMapping("/mis-proyectos")
    public String misProyectos(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth";
        }

        try {
            List<Proyecto> proyectos = proyectoService.getProyectosByProductor(userId);
            model.addAttribute("proyectos", proyectos);

            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userId", userId);
            model.addAttribute("userName", session.getAttribute("userName"));
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar los proyectos");
        }

        return "Productor/proyectosP";
    }

    @GetMapping("/crear-proyecto")
    public String mostrarFormularioCrear(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login1";
        }

        model.addAttribute("proyecto", new Proyecto());
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", session.getAttribute("userName"));

        return "Productor/crear-proyecto";
    }

    @PostMapping("/guardar-proyecto")
    public String guardarProyecto(@ModelAttribute Proyecto proyecto,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth";
        }

        try {
            Optional<UsuarioProductor> productorOpt = usuarioProductorCrudRep.findById(userId);
            if (productorOpt.isEmpty()) {
                redirectAttributes.addAttribute("error", "Productor no encontrado");
                return "redirect:/mis-proyectos";
            }

            proyecto.setDocProductor(productorOpt.get());

            boolean guardado = proyectoService.postNewProyecto(proyecto);
            if (guardado) {
                redirectAttributes.addAttribute("success", "Proyecto creado exitosamente");
            } else {
                redirectAttributes.addAttribute("error", "Error al guardar el proyecto");
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Error al crear el proyecto");
        }

        return "redirect:/mis-proyectos";
    }

    @GetMapping("/editar-proyecto/{id}")
    public String editarProyecto(@PathVariable Long id, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth";
        }

        Optional<Proyecto> proyectoOpt = proyectoService.getProyectoById(id);
        if (proyectoOpt.isEmpty()) {
            return "redirect:/mis-proyectos";
        }

        Proyecto proyecto = proyectoOpt.get();
        if (!proyecto.getDocProductor().getDocProductor().equals(userId)) {
            return "redirect:/mis-proyectos";
        }

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", session.getAttribute("userName"));

        return "Productor/editar-proyecto";
    }

    @PostMapping("/actualizar-proyecto/{id}")
    public String actualizarProyecto(@PathVariable Long id,
                                     @ModelAttribute Proyecto proyecto,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth";
        }

        try {
            Optional<Proyecto> proyectoExistenteOpt = proyectoService.getProyectoById(id);
            if (proyectoExistenteOpt.isEmpty()) {
                redirectAttributes.addAttribute("error", "Proyecto no encontrado");
                return "redirect:/mis-proyectos";
            }

            Proyecto proyectoExistente = proyectoExistenteOpt.get();
            if (!proyectoExistente.getDocProductor().getDocProductor().equals(userId)) {
                redirectAttributes.addAttribute("error", "No tienes permisos para editar este proyecto");
                return "redirect:/mis-proyectos";
            }

            proyecto.setCodigoProyecto(id);
            proyecto.setDocProductor(proyectoExistente.getDocProductor());

            Proyecto actualizado = proyectoService.upDateProyecto(id, proyecto);

            if (actualizado != null) {
                redirectAttributes.addAttribute("success", "Proyecto actualizado exitosamente");
            } else {
                redirectAttributes.addAttribute("error", "Error al actualizar el proyecto");
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Error al actualizar el proyecto");
        }

        return "redirect:/mis-proyectos";
    }

    @GetMapping("/eliminar-proyecto/{id}")
    public String eliminarProyecto(@PathVariable Long id,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth";
        }

        try {
            Optional<Proyecto> proyectoOpt = proyectoService.getProyectoById(id);
            if (proyectoOpt.isEmpty()) {
                redirectAttributes.addAttribute("error", "Proyecto no encontrado");
                return "redirect:/mis-proyectos";
            }

            Proyecto proyecto = proyectoOpt.get();
            if (!proyecto.getDocProductor().getDocProductor().equals(userId)) {
                redirectAttributes.addAttribute("error", "No tienes permisos para eliminar este proyecto");
                return "redirect:/mis-proyectos";
            }

            boolean eliminado = proyectoService.deleteProyecto(id);
            if (eliminado) {
                redirectAttributes.addAttribute("success", "Proyecto eliminado exitosamente");
            } else {
                redirectAttributes.addAttribute("error", "Error al eliminar el proyecto");
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Error al eliminar el proyecto");
        }

        return "redirect:/mis-proyectos";
    }
}
