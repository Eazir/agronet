/*
package com.tyrservices.agronetb.Controllers;

import com.campofrescobackend.modelos.DTOs.productoUpdateDTO;
import com.campofrescobackend.modelos.DTOs.unidadesStockDTO;
import com.campofrescobackend.modelos.entidades.Producto;
import com.campofrescobackend.modelos.entidades.StockProducto;
import com.campofrescobackend.servicios.productosServices.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


// SECTION DE REQUESTS-PRODUCTOS ---------------------------------------------------------------------------------------

    //GETS--------------------------------------------------

    @GetMapping()
    public ResponseEntity<List<Producto>> getAllProducto() {
        return productoService.getAllProducto().isEmpty()?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(productoService.getAllProducto());
    }

    @GetMapping("/productor=>{idProductor}")
    public ResponseEntity<List<Producto>> findAllByCProductor(@PathVariable int idProductor) {
        return productoService.getAllByProductor(idProductor) == null?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(productoService.getAllByProductor(idProductor));
    }

    @GetMapping("/categoria=>{idCategoria}")
    public ResponseEntity<List<Producto>> findAllByCategoria(@PathVariable int idCategoria) {
        return productoService.getAllByCategoria(idCategoria) == null?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(productoService.getAllByCategoria(idCategoria));
    }

    @GetMapping("/nombre=>{nombreProducto}")
    public ResponseEntity<List<Producto>> findAllByNombre(@PathVariable String nombreProducto) {
        return productoService.getAllByNombreProducto(nombreProducto) == null?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(productoService.getAllByNombreProducto(nombreProducto));
    }


    //POSTs--------------------------------------------------------------------------

    @PostMapping("/nuevo-producto")
    public ResponseEntity<Boolean> postNewProducto(@RequestBody Producto producto) {
         return !productoService.postNewProducto(producto) ?
                ResponseEntity.badRequest().build():
                ResponseEntity.ok(true);
    }


    //PUTs-PATCHs------------------------------------------------------------------------

    @PatchMapping("/actualizar=>{idProducto}")
    public ResponseEntity<Producto> upDateProducto(
            @PathVariable int idProducto,
            @RequestBody productoUpdateDTO producto) {
        Producto updatedProducto = productoService.upDateProducto(idProducto, producto);
        return updatedProducto == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(updatedProducto);
    }


    //DELETEs---------------------------------------------------------------------

    @DeleteMapping("/eliminar=>{idProducto}")
    public ResponseEntity<Boolean> deleteProducto(@PathVariable int idProducto) {
        return productoService.deleteProducto(idProducto) ?
                ResponseEntity.ok(true):
                ResponseEntity.badRequest().build();
    }


// SECTION DE REQUESTS-STOCK -------------------------------------------------------------------------------------------

    //GETS-------------------------------------------------------------------------

    @GetMapping("/stock")
    public ResponseEntity<List<StockProducto>> getAllStock(){
        List<StockProducto> listadoStock = productoService.getAllStock();
        return listadoStock.isEmpty()?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(listadoStock);
    }

    @GetMapping("/stock-producto=>{idProducto}")
    public ResponseEntity<StockProducto> getStockByProducto(@PathVariable int idProducto){
        Optional<StockProducto> stockProducto = productoService.getStockByProducto(idProducto);
        return stockProducto.isEmpty()?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(stockProducto.get());
    }

    @GetMapping("/unidades-producto=>{idProducto}")
    public ResponseEntity<unidadesStockDTO> ReturnUnidadesStock(@PathVariable int idProducto){
        unidadesStockDTO unidadesStock= productoService.getUnidadesStock(idProducto);
        return unidadesStock == null?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(unidadesStock);
    }

    //POSTs------------------------------------------------------------------------

    @PostMapping("/nuevo-stock")
    public ResponseEntity<Boolean> postNewStockProducto(@RequestBody StockProducto stock){

        return !productoService.postNewStockProducto(stock)?
                ResponseEntity.badRequest().build():
                ResponseEntity.ok(true);
    }



}
*/
