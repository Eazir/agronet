package com.tyrservices.agronetb.Services.ProyectosService;

import com.tyrservices.agronetb.Models.entidades.Proyecto;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import com.tyrservices.agronetb.Repositorys.ProyectoCrudRep;
import com.tyrservices.agronetb.Repositorys.UsuarioProductorCrudRep;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImp implements ProyectoService {

    private final ProyectoCrudRep proyectoCrudRep;
    private final UsuarioProductorCrudRep usuarioProductorCrudRep;

    public ProyectoServiceImp(ProyectoCrudRep proyectoCrudRep,
                              UsuarioProductorCrudRep usuarioProductorCrudRep) {
        this.proyectoCrudRep = proyectoCrudRep;
        this.usuarioProductorCrudRep = usuarioProductorCrudRep;
    }

    @Override
    public List<Proyecto> getAllProyectos() {
        return proyectoCrudRep.findAll();
    }

    @Override
    public List<Proyecto> getProyectosByProductor(Long idProductor) {
        Optional<UsuarioProductor> productorOpt = usuarioProductorCrudRep.findById(idProductor);
        if (productorOpt.isEmpty()) {
            return List.of();
        }
        return proyectoCrudRep.findProyectosByDocProductor(productorOpt.get());
    }

    @Override
    public Optional<Proyecto> getProyectoById(Long id) {
        return proyectoCrudRep.findById(id);
    }

    @Override
    public boolean postNewProyecto(Proyecto proyecto) {
        try {
            proyectoCrudRep.save(proyecto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Proyecto upDateProyecto(Long id, Proyecto proyectoActualizado) {
        Optional<Proyecto> proyectoOpt = proyectoCrudRep.findById(id);
        if (proyectoOpt.isEmpty()) {
            return null;
        }

        Proyecto existente = proyectoOpt.get();
        existente.setNombreProyecto(proyectoActualizado.getNombreProyecto());
        existente.setDescripcion(proyectoActualizado.getDescripcion());
        existente.setMeta(proyectoActualizado.getMeta());
        existente.setUrlImagen(proyectoActualizado.getUrlImagen());

        return proyectoCrudRep.save(existente);
    }

    @Override
    public boolean deleteProyecto(Long id) {
        if (proyectoCrudRep.findById(id).isEmpty()) {
            return false;
        }
        try {
            proyectoCrudRep.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
