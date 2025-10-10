/*
package com.tyrservices.agronetb.Services.ProyectosService;

import com.campofrescobackend.modelos.DTOs.proyectoDonacionesUpdateDTO;
import com.campofrescobackend.modelos.DTOs.proyectoInfoUpdateDTO;
import com.campofrescobackend.modelos.entidades.Proyectos;
import com.campofrescobackend.repositorio.ProyectoCrudRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImp implements ProyectoService{
    //Implementaciones
    private final ProyectoCrudRep proyectoCrudRep;

    @Autowired
    public ProyectoServiceImp(
            ProyectoCrudRep proyectoCrudRep) {
        this.proyectoCrudRep = proyectoCrudRep;
    }

    //Get´s

    */
/**
     * Obtener todos los proyectos
     * @return : lista con todos los productos que encuentre
     *//*

    @Override
    public List<Proyectos> getAllProyectos() {
        return proyectoCrudRep.findAll();
    }

    */
/**
     * Obtener todos los proyectos de un usuario productor
     * @param idProductor : recibe el ID del productor
     * @return lista con todos los productos del usuario productor
     *//*

    @Override
    public  List<Proyectos> getProyectosByProductor(int idProductor) {
        return proyectoCrudRep.findProyectosByDocProductor(idProductor);
    }

    */
/**
     * Obtener todos los proyectos que contengan un nombre en particular
     * @param nombreProyecto : recibe el nombre del proyecto
     * @return todos los proyectos que contengan el nombre proporcionado
     *//*

    @Override
    public List<Proyectos> getProyectosByNombre(String nombreProyecto) {
        return proyectoCrudRep.findProyectosByNombreProyectoContainingIgnoreCase(nombreProyecto);
    }


    //POSTs

    */
/**
     * Crear un nuevo proyecto en el sistema
     *
     * @param proyectos : recibe el proyecto a guardar
     * @return el proyecto guardado
     *//*

    @Override
    public boolean postNewProyecto(Proyectos proyectos){
        try {
            proyectoCrudRep.save(proyectos);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    //PUTs-PATCHs

    */
/**
     * Actualiza la información de un proyecto del sistema
     * @param pr_code : recibe el ID del producto a actualizar
     * @param dto : recibe el producto con los datos a actualizar
     * @return el proyecto con sus datos actualizados
     *//*

    @Override
    public Proyectos upDateInfoProyecto(int pr_code, proyectoInfoUpdateDTO dto){
        Optional<Proyectos> proyectos = proyectoCrudRep.findById(pr_code);
        if (proyectos.isEmpty()){
            return null;
        }

        if(dto.getNombreProyecto() != null){
            proyectos.get().setNombreProyecto(dto.getNombreProyecto());
        }
        if(dto.getDescripcion() != null){
            proyectos.get().setDescripcion(dto.getDescripcion());
        }
        if(dto.getMeta() != null){
            proyectos.get().setMeta(dto.getMeta());
        }
        if(dto.getUrlImagen() != null){
            proyectos.get().setUrlImagen(dto.getUrlImagen());
        }
        return proyectoCrudRep.save( proyectos.get());
    }

    */
/**
     * Actualiza la información de donaciones de un proyecto del sistema
     * @param pr_code : recibe el ID del producto a actualizar
     * @param pr : recibe el producto con los datos a actualizar
     * @return el proyecto con sus datos acerca de donaciones actualizados
     *//*

    @Override
    public Proyectos upDateProgresoProyecto(int pr_code, proyectoDonacionesUpdateDTO pr){
        Proyectos proyectos = proyectoCrudRep.findById(pr_code).isPresent()?
                proyectoCrudRep.findById(pr_code).get():
                null;
        if(proyectos != null){
            if(pr.getRecaudado() != null){
                proyectos.setRecaudado(pr.getRecaudado());
            }
            if(pr.getDonacionesRecibidas() != null){
                proyectos.setDonacionesRecibidas(pr.getDonacionesRecibidas());
            }
            return proyectoCrudRep.saveAndFlush(proyectos);
        }else {
            return null;
        }
    }


    //DELETEs

    */
/**
     * Elimina un producto del sistema
     * @param pr_code: recibe el ID del producto a eliminar
     * @return true si se eliminó el producto, false en caso contrario
     *//*

    public boolean deleteProyecto(int pr_code) {
        Proyectos proyectos = proyectoCrudRep.findById(pr_code).isPresent() ?
                proyectoCrudRep.findById(pr_code).get() : null;
        if (proyectos != null) {
            proyectoCrudRep.delete(proyectos);
            return true;
        }else{
            return false;
        }
    }

}*/
