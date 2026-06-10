package com.tyrservices.agronetb.Services.ProyectosService;

import com.tyrservices.agronetb.Models.entidades.Proyecto;

import java.util.List;
import java.util.Optional;

public interface ProyectoService {
    List<Proyecto> getAllProyectos();
    List<Proyecto> getProyectosByProductor(Long idProductor);
    Optional<Proyecto> getProyectoById(Long id);
    boolean postNewProyecto(Proyecto proyecto);
    Proyecto upDateProyecto(Long id, Proyecto proyecto);
    boolean deleteProyecto(Long id);
}
