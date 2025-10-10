package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.Proyecto;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectoCrudRep extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findProyectosByNombreProyectoContainingIgnoreCase(String nombreProyecto);
    List<Proyecto> findProyectosByDocProductor(UsuarioProductor docProductor);
}
