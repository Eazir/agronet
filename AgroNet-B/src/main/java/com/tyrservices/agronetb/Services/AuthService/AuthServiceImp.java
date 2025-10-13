package com.tyrservices.agronetb.Services.AuthService;

import com.tyrservices.agronetb.Models.DTOs.UsuarioDTO;
import com.tyrservices.agronetb.Models.entidades.TipoDocumento;
import com.tyrservices.agronetb.Models.entidades.UsuarioConsumidor;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import com.tyrservices.agronetb.Repositorys.TipoDocumentoCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioConsumidorCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioProductorCrudRep;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService {

    //implementaciones
    private final UsuarioProductorCrudRep usuarioProductorCrudRep;
    private final UsuarioConsumidorCrudRep usuarioConsumidorCrudRep;
    private final TipoDocumentoCrudRep tipoDocumentoCrudRep;

    @Autowired
    public AuthServiceImp(UsuarioConsumidorCrudRep usuarioConsumidorCrudRep,
                             UsuarioProductorCrudRep usuarioProductorCrudRep,
                          TipoDocumentoCrudRep tipoDocumentoCrudRep){
        this.usuarioConsumidorCrudRep = usuarioConsumidorCrudRep;
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
        this.tipoDocumentoCrudRep = tipoDocumentoCrudRep;
    }

    /**
     * Método para validar formato de email
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }


    @Override
    public String registro(UsuarioDTO usuarioN, String confirmarContraseña, String tipo, Model model, RedirectAttributes redirectAttributes) {
        boolean hasErrors = false;

        // Validaciones
        if (usuarioN.getDocUsuario() == null) {
            model.addAttribute("errorDocProductor", "El documento es obligatorio");
            hasErrors = true;
        }

        if (usuarioN.getCodigoDoc() == null) {
            model.addAttribute("errorCodigoDoc", "El tipo de documento es obligatorio");
            hasErrors = true;
        }

        if (usuarioN.getTipoUsuario() == null || usuarioN.getTipoUsuario().trim().isEmpty()) {
            model.addAttribute("errorTipoUsuario", "El tipo de persona es obligatorio");
            hasErrors = true;
        }

        if (usuarioN.getNombreUsuario() == null || usuarioN.getNombreUsuario().trim().isEmpty()) {
            model.addAttribute("errorNombreUsuario", "El nombre de usuario es obligatorio");
            hasErrors = true;
        } else if (usuarioN.getNombreUsuario().length() < 3) {
            model.addAttribute("errorNombreUsuario", "El nombre de usuario debe tener al menos 3 caracteres");
            hasErrors = true;
        }

        if (usuarioN.getEmail() == null || usuarioN.getEmail().trim().isEmpty()) {
            model.addAttribute("errorEmail", "El email es obligatorio");
            hasErrors = true;
        } else if (!isValidEmail(usuarioN.getEmail())) {
            model.addAttribute("errorEmail", "El formato del email no es válido");
            hasErrors = true;
        }

        if (usuarioN.getContraseña() == null || usuarioN.getContraseña().trim().isEmpty()) {
            model.addAttribute("errorContraseña", "La contraseña es obligatoria");
            hasErrors = true;
        } else if (usuarioN.getContraseña().length() < 8) {
            model.addAttribute("errorContraseña", "La contraseña debe tener al menos 8 caracteres");
            hasErrors = true;
        }

        // Validar confirmación de contraseña
        if (!usuarioN.getContraseña().equals(confirmarContraseña)) {
            model.addAttribute("errorConfirmarContraseña", "Las contraseñas no coinciden");
            hasErrors = true;
        }

        //validaciones productor
        if (tipo.equals("1")){
            // Verificar si el email ya existe
            if (usuarioN.getEmail() != null && usuarioProductorCrudRep.existsByEmail(usuarioN.getEmail())) {
                model.addAttribute("errorEmail", "El email ya está registrado");
                hasErrors = true;
            }

            // Verificar si el nombre de usuario ya existe
            if (usuarioN.getNombreUsuario() != null && usuarioProductorCrudRep.existsByNombreUsuario(usuarioN.getNombreUsuario())) {
                model.addAttribute("errorNombreUsuario", "El nombre de usuario ya está en uso");
                hasErrors = true;
            }

            // Verificar si el documento ya existe
            if (usuarioN.getDocUsuario() != null && usuarioProductorCrudRep.existsByDocProductor(usuarioN.getDocUsuario())) {
                model.addAttribute("errorDocProductor", "El número de documento ya está registrado");
                hasErrors = true;
            }
            //validaciones consumidor
        }else if (tipo.equals("2")){
            // Verificar si el email ya existe
            if (usuarioN.getEmail() != null && usuarioConsumidorCrudRep.existsByEmail((usuarioN.getEmail()))) {
                model.addAttribute("errorEmail", "El email ya está registrado");
                hasErrors = true;
            }

            // Verificar si el nombre de usuario ya existe
            if (usuarioN.getNombreUsuario() != null && usuarioConsumidorCrudRep.existsByNombreUsuario((usuarioN.getNombreUsuario()))) {
                model.addAttribute("errorNombreUsuario", "El nombre de usuario ya está en uso");
                hasErrors = true;
            }

            // Verificar si el documento ya existe
            if (usuarioN.getDocUsuario() != null && usuarioConsumidorCrudRep.existsByDocConsumidor(usuarioN.getDocUsuario())) {
                model.addAttribute("errorDocProductor", "El número de documento ya está registrado");
                hasErrors = true;
            }
        }else {
            model.addAttribute("tipo", tipo);
            return "registro";
        }


        if (hasErrors) {
            // Recargar tipos de documento para el dropdown
            List<TipoDocumento> tiposDocumento = tipoDocumentoCrudRep.findAll();
            model.addAttribute("tiposDocumento", tiposDocumento);
            // Agregar este atributo para mostrar el mensaje general de error
            model.addAttribute("tieneErrores", true);
            model.addAttribute("tipo", tipo);
            return "registro";
        }

        try {
            // Guardar el nuevo productor
            if (tipo.equals("1")) {
                UsuarioProductor uN = new UsuarioProductor();

                uN.setDocProductor(usuarioN.getDocUsuario());
                uN.setCodigoDoc(usuarioN.getCodigoDoc());
                uN.setNombreUsuario(usuarioN.getNombreUsuario());
                uN.setEmail(usuarioN.getEmail());
                uN.setContraseña(usuarioN.getContraseña());
                uN.setTipoUsuario(usuarioN.getTipoUsuario());

                usuarioProductorCrudRep.save(uN);
            }else if (tipo.equals("2")) {
                UsuarioConsumidor uN = new UsuarioConsumidor();

                uN.setDocConsumidor(usuarioN.getDocUsuario());
                uN.setCodigoDoc(usuarioN.getCodigoDoc());
                uN.setNombreUsuario(usuarioN.getNombreUsuario());
                uN.setEmail(usuarioN.getEmail());
                uN.setContraseña(usuarioN.getContraseña());
                uN.setTipoUsuario(usuarioN.getTipoUsuario());

                usuarioConsumidorCrudRep.save(uN);
            }

            redirectAttributes.addFlashAttribute(
                    "registroExitoso", "¡Registro exitoso! Ahora puedes iniciar sesión.");

            return "redirect:/login" + tipo;

        } catch (Exception e) {
            model.addAttribute("errorGeneral", "Error al registrar el usuario: " + e.getMessage());
            List<TipoDocumento> tiposDocumento = tipoDocumentoCrudRep.findAll();
            model.addAttribute("tiposDocumento", tiposDocumento);
            model.addAttribute("tieneErrores", true);
            model.addAttribute("tipo", tipo);
            return "registro";
        }

    }

    @Override
    public String login(String username, String password, String tipo, HttpSession session, RedirectAttributes redirectAttributes) {

        try {

            if (tipo.equals("1")) {

                // Buscar usuario por email
                Optional<UsuarioProductor> usuarioOpt = usuarioProductorCrudRep.findByEmail((username));

                // Verificar si el usuario existe
                if (usuarioOpt.isEmpty()) {
                    redirectAttributes.addAttribute("error", true);

                    return "redirect:/login" + tipo;
                }

                // Verificar contraseña
                if (usuarioOpt.get().getContraseña().equals(password)) {
                    // Credenciales válidas
                    session.setAttribute("userId", usuarioOpt.get().getDocProductor());
                    session.setAttribute("userName", usuarioOpt.get().getNombreUsuario());
                    session.setAttribute("userEmail", usuarioOpt.get().getEmail());
                    session.setAttribute("tipo", tipo);
                } else {
                    // Contraseña incorrecta
                    redirectAttributes.addAttribute("error", true);
                    return "redirect:/login" + tipo;
                }


            }else if (tipo.equals("2")) {

                // Buscar usuario por email
                Optional<UsuarioConsumidor> usuarioOpt = usuarioConsumidorCrudRep.findByEmail(((username)));

                // Verificar si el usuario existe
                if (usuarioOpt.isEmpty()) {
                    redirectAttributes.addAttribute("error", true);
                    return "redirect:/login" + tipo;
                }

                // Verificar contraseña
                if (usuarioOpt.get().getContraseña().equals(password)) {

                    // Credenciales válidas
                    session.setAttribute("userId", usuarioOpt.get().getDocConsumidor());
                    session.setAttribute("userName", usuarioOpt.get().getNombreUsuario());
                    session.setAttribute("userEmail", usuarioOpt.get().getEmail());
                    session.setAttribute("tipo", tipo);
                } else {
                    // Contraseña incorrecta
                    redirectAttributes.addAttribute("error", true);
                    return "redirect:/login" + tipo;
                }
            }
        } catch (Exception e) {
            // Error en el proceso
            redirectAttributes.addAttribute("error", true);
            return "redirect:/login" + tipo;
        }

        return "redirect:/panel-control";
    }
}
