/*
package com.tyrservices.agronetb.Controllers;

import com.campofrescobackend.modelos.DTOs.proyectoDonacionesUpdateDTO;
import com.campofrescobackend.modelos.DTOs.proyectoInfoUpdateDTO;
import com.campofrescobackend.modelos.entidades.Proyectos;
import com.campofrescobackend.servicios.proyectosServices.ProyectoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
public class ProyectosController {

    private final ProyectoService proyectoService;

    public ProyectosController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    //GETS

    @GetMapping()
    public ResponseEntity<List<Proyectos>> getAllProyectos() {
        List<Proyectos> proyectosList = proyectoService.getAllProyectos();
        return proyectosList.isEmpty()?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(proyectosList);
    }

    @GetMapping("/proyecto=>{idProductor}")
    public ResponseEntity<List<Proyectos>> getProyectosByProductor(@PathVariable int idProductor) {
        List<Proyectos> proyectosList = proyectoService.getProyectosByProductor(idProductor);
        return proyectosList.isEmpty()?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(proyectosList);
    }

    @GetMapping("/nombre=>{nombreProyecto}")
    public ResponseEntity<List<Proyectos>> getProyectosByNombre(@PathVariable String nombreProyecto) {
        List<Proyectos> proyectosList = proyectoService.getProyectosByNombre(nombreProyecto);
        return proyectosList.isEmpty()?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(proyectosList);
    }

   //POSTs

    @PostMapping("/nuevo-proyecto")
    public ResponseEntity<Boolean> postNewProyecto(@RequestBody Proyectos proyectos) {
        return !proyectoService.postNewProyecto(proyectos)?
                ResponseEntity.badRequest().build():
                ResponseEntity.ok(true);
    }

    //PUTs-PATCHs

    @PatchMapping("/actualizarInfo=>{idProyecto}")
    public ResponseEntity<Proyectos> upDateInfoProyecto(
            @PathVariable int idProyecto,
            @RequestBody proyectoInfoUpdateDTO pr){
        Proyectos updatedProyectos = proyectoService.upDateInfoProyecto(idProyecto, pr);
        return updatedProyectos == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(updatedProyectos);
    }

    @PatchMapping("/actualizarProgres=>{idProyecto}")
    public ResponseEntity<Proyectos> upDateProgresoProyecto(
            @PathVariable int idProyecto,
            @RequestBody proyectoDonacionesUpdateDTO pr){
        Proyectos updatedProyectos = proyectoService.upDateProgresoProyecto(idProyecto, pr);
        return updatedProyectos == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(updatedProyectos);
    }
*/

    //DELETEs

/*  @DeleteMapping("/elimibar=>{idProyecto}")
    public ResponseEntity<Boolean> deleteProducto(@PathVariable int idProyecto) {
        return productoServiceImp.deleteProducto(idProyecto) ?
                ResponseEntity.ok(true):
                ResponseEntity.badRequest().build();
    }*/

//}










