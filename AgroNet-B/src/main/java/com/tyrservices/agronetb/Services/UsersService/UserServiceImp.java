package com.tyrservices.agronetb.Services.UsersService;

import com.tyrservices.agronetb.Models.entidades.UsuarioConsumidor;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import com.tyrservices.agronetb.Repositorys.TipoDocumentoCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioConsumidorCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioProductorCrudRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UsersService{
    //implementaciones
    private final UsuarioProductorCrudRep usuarioProductorCrudRep;
    private final UsuarioConsumidorCrudRep usuarioConsumidorCrudRep;
    private final TipoDocumentoCrudRep tipoDocumentoCrudRep;

    @Autowired
    public UserServiceImp(UsuarioConsumidorCrudRep usuarioConsumidorCrudRep,
                          UsuarioProductorCrudRep usuarioProductorCrudRep,
                          TipoDocumentoCrudRep tipoDocumentoCrudRep){
        this.usuarioConsumidorCrudRep = usuarioConsumidorCrudRep;
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
        this.tipoDocumentoCrudRep = tipoDocumentoCrudRep;
    }


    @Override
    public boolean actualizarProductor(UsuarioProductor usuarioAct) {
        UsuarioProductor existente = usuarioProductorCrudRep.findById(usuarioAct.getDocProductor())
                .orElseThrow(() -> new RuntimeException("Productor no encontrado"));

        existente.setTipoUsuario(usuarioAct.getTipoUsuario());
        existente.setNombreUsuario(usuarioAct.getNombreUsuario());
        existente.setEmail(usuarioAct.getEmail());

        usuarioProductorCrudRep.save(existente);
        return true;
    }

    @Override
    public boolean cambiarContraseñaP(Long userId, String currentPassword, String newPassword) {

        Optional<UsuarioProductor> productorOpt = usuarioProductorCrudRep.findById(userId);

        if (productorOpt.isPresent()) {
            UsuarioProductor productor = productorOpt.get();

            // Verificar contraseña actual
            if (productor.getContraseña().equals(currentPassword)) {
                // Actualizar contraseña
                productor.setContraseña(newPassword);
                usuarioProductorCrudRep.save(productor);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean actualizarConsumidor(UsuarioConsumidor usurioAct) {
        return false;
    }
}
