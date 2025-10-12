package com.tyrservices.agronetb.Controllers;

import com.tyrservices.agronetb.Models.entidades.TipoDocumento;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import com.tyrservices.agronetb.Repositorys.TipoDocumentoCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioConsumidorCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioProductorCrudRep;
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

    public AuthController(UsuarioConsumidorCrudRep usuarioConsumidorCrudRep,
                                UsuarioProductorCrudRep usuarioProductorCrudRep, TipoDocumentoCrudRep tipoDocumentoCrudRep) {
        this.usuarioConsumidorCrudRep = usuarioConsumidorCrudRep;
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
        this.tipoDocumentoCrudRep = tipoDocumentoCrudRep;
    }


    //auth para productores

    @GetMapping("/LoginProductor")
    public String loginPageProductor(@RequestParam(value = "error", required = false) String error,
                                     Model model) {

        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Por favor, verifica tus datos.");
        }

        return "Productor/loginP";
    }

    @PostMapping("/LoginProductor")
    public String processLogin(
            @RequestParam("user") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {

        try {
            // Buscar usuario por email
            Optional<UsuarioProductor> productorOpt = usuarioProductorCrudRep.findByEmail((username));

            // Verificar si el usuario existe
            if (!productorOpt.isPresent()) {
                redirectAttributes.addAttribute("error", true);
                return "redirect:/LoginProductor";
            }

            UsuarioProductor productor = productorOpt.get();

            // Verificar contraseña
            if (productor.getContraseña().equals(password)) {
                // Credenciales válidas, Redirigir al panel con el I
                return "redirect:/Control-Panel-P" + productor.getDocProductor().toString();
            } else {
                // Contraseña incorrecta
                redirectAttributes.addAttribute("error", true);
                return "redirect:/LoginProductor";
            }

        } catch (Exception e) {
            // Error en el proceso
            redirectAttributes.addAttribute("error", true);
            return "redirect:/LoginProductor";
        }
    }


    @GetMapping("/SingUpProductor")
    public String registroPageProductor(Model model) {
        // Obtener tipos de documento para el formulario
        List<TipoDocumento> tiposDocumento = tipoDocumentoCrudRep.findAll();

        model.addAttribute("productor", new UsuarioProductor());
        model.addAttribute("tiposDocumento", tiposDocumento);
        return "Productor/registroP";

    }

    /**
     * Procesa el formulario de registro
     */
    @PostMapping("/SingUpProductor/post")
    public String procesarRegistro(@ModelAttribute("productor") UsuarioProductor productor,
                                      @RequestParam("confirmarContraseña") String confirmarContraseña,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {

        boolean hasErrors = false;

        // Validaciones manuales
        if (productor.getDocProductor() == null) {
            model.addAttribute("errorDocProductor", "El documento es obligatorio");
            hasErrors = true;
        }

        if (productor.getCodigoDoc() == null) {
            model.addAttribute("errorCodigoDoc", "El tipo de documento es obligatorio");
            hasErrors = true;
        }

        if (productor.getTipoUsuario() == null || productor.getTipoUsuario().trim().isEmpty()) {
            model.addAttribute("errorTipoUsuario", "El tipo de persona es obligatorio");
            hasErrors = true;
        }

        if (productor.getNombreUsuario() == null || productor.getNombreUsuario().trim().isEmpty()) {
            model.addAttribute("errorNombreUsuario", "El nombre de usuario es obligatorio");
            hasErrors = true;
        } else if (productor.getNombreUsuario().length() < 3) {
            model.addAttribute("errorNombreUsuario", "El nombre de usuario debe tener al menos 3 caracteres");
            hasErrors = true;
        }

        if (productor.getEmail() == null || productor.getEmail().trim().isEmpty()) {
            model.addAttribute("errorEmail", "El email es obligatorio");
            hasErrors = true;
        } else if (!isValidEmail(productor.getEmail())) {
            model.addAttribute("errorEmail", "El formato del email no es válido");
            hasErrors = true;
        }

        if (productor.getContraseña() == null || productor.getContraseña().trim().isEmpty()) {
            model.addAttribute("errorContraseña", "La contraseña es obligatoria");
            hasErrors = true;
        } else if (productor.getContraseña().length() < 8) {
            model.addAttribute("errorContraseña", "La contraseña debe tener al menos 8 caracteres");
            hasErrors = true;
        }

        // Validar confirmación de contraseña
        if (!productor.getContraseña().equals(confirmarContraseña)) {
            model.addAttribute("errorConfirmarContraseña", "Las contraseñas no coinciden");
            hasErrors = true;
        }

        // Verificar si el email ya existe
        if (productor.getEmail() != null && usuarioProductorCrudRep.existsByEmail(productor.getEmail())) {
            model.addAttribute("errorEmail", "El email ya está registrado");
            hasErrors = true;
        }

        // Verificar si el nombre de usuario ya existe
        if (productor.getNombreUsuario() != null && usuarioProductorCrudRep.existsByNombreUsuario(productor.getNombreUsuario())) {
            model.addAttribute("errorNombreUsuario", "El nombre de usuario ya está en uso");
            hasErrors = true;
        }

        // Verificar si el documento ya existe
        if (productor.getDocProductor() != null && usuarioProductorCrudRep.existsByDocProductor(productor.getDocProductor())) {
            model.addAttribute("errorDocProductor", "El número de documento ya está registrado");
            hasErrors = true;
        }

        if (hasErrors) {
            // Recargar tipos de documento para el dropdown
            List<TipoDocumento> tiposDocumento = tipoDocumentoCrudRep.findAll();
            model.addAttribute("tiposDocumento", tiposDocumento);
            // Agregar este atributo para mostrar el mensaje general de error
            model.addAttribute("tieneErrores", true);
            return "Productor/registroP";
        }

        try {
            // Guardar el nuevo productor
            usuarioProductorCrudRep.save(productor);
            redirectAttributes.addFlashAttribute(
                    "registroExitoso", "¡Registro exitoso! Ahora puedes iniciar sesión.");

            return "redirect:/LoginProductor";

        } catch (Exception e) {
            model.addAttribute("errorGeneral", "Error al registrar el usuario: " + e.getMessage());
            List<TipoDocumento> tiposDocumento = tipoDocumentoCrudRep.findAll();
            model.addAttribute("tiposDocumento", tiposDocumento);
            model.addAttribute("tieneErrores", true);
            return "Productor/registroP";
        }
    }
    /**
     * Método para validar formato de email
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}