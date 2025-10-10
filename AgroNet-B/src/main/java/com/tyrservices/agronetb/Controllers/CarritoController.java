/*
package com.tyrservices.agronetb.Controllers;

import com.campofrescobackend.modelos.entidades.Carrito;
import com.campofrescobackend.modelos.entidades.ProductoCarrito;
import com.campofrescobackend.servicios.carritoServices.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    //GETS
    @GetMapping("/carrito=>{idCarrito}")
    public ResponseEntity<Carrito>getCarritoById(@PathVariable int idCarrito) {
        Optional<Carrito> carrito =  carritoService.getCarritoById(idCarrito);
        return carrito.isEmpty()?
                ResponseEntity.badRequest().build():
                ResponseEntity.ok(carrito.get());
    }

    @GetMapping("/carrito-usuario=>{docUsuario}")
    public ResponseEntity<Carrito>getCarritoByUsuario(@PathVariable int docUsuario) {
        Optional<Carrito> carrito =  carritoService.getCarritoByUsuario(docUsuario);
        return carrito.isEmpty()?
                ResponseEntity.badRequest().build():
                ResponseEntity.ok(carrito.get());
    }

    //GETs
    @GetMapping("/productos-carrito=>{codigo_carrito}")
    public ResponseEntity<List<ProductoCarrito>> getproductosCarrito(@PathVariable Integer codigo_carrito){
        return ResponseEntity.ok(carritoService.getproductosCarrito(codigo_carrito));
    }

    //POSTs
    @PostMapping("/agregar-producto")
    public ResponseEntity<Boolean> saveProductoCarrito(@RequestBody ProductoCarrito productoCarrito){
        return ResponseEntity.ok(carritoService.saveProductoCarrito(productoCarrito));
    }

    //DELETEs
    @DeleteMapping("/eliminar-producto=>{idProducto}")
    public ResponseEntity<Boolean> deleteProductoCarrito(@PathVariable int idProducto){
        return ResponseEntity.ok(carritoService.deleteProductoCarrito(idProducto));
    }
}*/
