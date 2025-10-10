/*
package com.tyrservices.agronetb.Services.ProductosService;

import com.campofrescobackend.modelos.DTOs.productoUpdateDTO;
import com.campofrescobackend.modelos.DTOs.stockProductosUpdateDTO;
import com.campofrescobackend.modelos.DTOs.unidadesStockDTO;
import com.campofrescobackend.modelos.entidades.Producto;
import com.campofrescobackend.modelos.entidades.StockProducto;
import com.campofrescobackend.repositorio.ProductoCrudRep;
import com.campofrescobackend.repositorio.StockProductoCrudRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductoServiceImp implements ProductoService{

    //Implementaciones
    private final ProductoCrudRep productoCrudRep;
    private final StockProductoCrudRep stockProductoCrudRep;

    @Autowired
    public ProductoServiceImp(
            ProductoCrudRep productoCrudRep,
            StockProductoCrudRep stockProductoCrudRep) {
        this.productoCrudRep = productoCrudRep;
        this.stockProductoCrudRep = stockProductoCrudRep;
    }


//Services de Productos-------------------------------------------------------------------------------------------------

    //GETs----------

    */
/**
     * Obtener todos los productos
     * @return : lista con todos los productos que encuentre
     *//*

    @Override
    public List<Producto> getAllProducto() {
        return productoCrudRep.findAll();
    }

    */
/**
     * Obtener todos los productos de un usuario productor
     * @param idProductor : recibe el ID del productor
     * @return lista con todos los productos del usuario productor
     *//*

    @Override
    public List<Producto> getAllByProductor(int idProductor) {
        return productoCrudRep.findProductosByDocProductor(idProductor);
    }

    */
/**
     * Obtener todos los productos de una categoria
     * @param idCategoria : recibe ID de la categoria del producto
     * @return lista con todos los productos pertenecientes a la categoria productos
     *//*

    @Override
    public List<Producto> getAllByCategoria(int idCategoria) {
        return productoCrudRep.findProductosByCodigoCategoria(idCategoria);
    }

    */
/**
     * Obtener todos los productos que contengan un nombre en particular
     * @param nombre : recibe el nombre del producto
     * @return todos los productos que contengan el nombre proporcionado
     *//*

    @Override
    public List<Producto> getAllByNombreProducto(String nombre) {
        return productoCrudRep.findByNombreProductoContainingIgnoreCase(nombre);
    }


    //POSTs----------

    */
/**
     * Crear un nuevo producto en el sistema
     *
     * @param producto : recibe el producto a guardar
     * @return el producto guardado
     *//*

    @Override
    public boolean postNewProducto(Producto producto){
        try {
            productoCrudRep.save(producto);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    //PUTs-PATCHs----------

    */
/**
     * Actualiza un producto del sistema
     * @param codigo_producto : recibe el ID del producto a actualizar
     * @param dto : recibe el producto con los datos a actualizar
     * @return el producto actualizado
     *//*

    @Override
    public Producto upDateProducto(int codigo_producto, productoUpdateDTO dto) {
        Optional<Producto> producto = productoCrudRep.findById(codigo_producto);
        if(producto.isEmpty()) {
            return null;
        }

        if(dto.getCodigoCategoria() != null){
            producto.get().setCodigoCategoria(dto.getCodigoCategoria());
        }
        if(dto.getNombreProducto() != null){
            producto.get().setNombreProducto(dto.getNombreProducto());
        }
        if(dto.getTipoProducto() != null){
            producto.get().setTipoProducto(dto.getTipoProducto());
        }
        if(dto.getOrigenProducto() != null){
            producto.get().setOrigenProducto(dto.getOrigenProducto());
        }
        if(dto.getUnidadPrecio() != null){
            producto.get().setUnidadPrecio(dto.getUnidadPrecio());
        }
        if(dto.getDescripcion() != null){
            producto.get().setDescripcion(dto.getDescripcion());
        }
        if(dto.getPrecio() != null){
            producto.get().setPrecio(dto.getPrecio());
        }
        return productoCrudRep.save(producto.get());
    }


    //DELETEs----------

    */
/**
     * Elimina un producto del sistema
     * @param codigo_producto : recibe el ID del producto a eliminar
     * @return true si se eliminó el producto, false en caso contrario
     *//*

    public boolean deleteProducto(int codigo_producto){
        Optional<Producto> producto = productoCrudRep.findById(codigo_producto);
        if (producto.isEmpty()){
            return false;
        }

        productoCrudRep.delete(producto.get());
        deleteStockProducto(codigo_producto);
        return true;
    }


//Services de Stock de los productos------------------------------------------------------------------------------------

    //GETs----------

    */
/**
     * Obtener el stock de todos los productos
     * @return lista con todos los productos que encuentre en el stock
     *//*

    @Override
    public List<StockProducto> getAllStock() {
        return stockProductoCrudRep.findAll();
    }

    */
/**
     * Obtener el stock de un producto
     *
     * @param codigo_producto : recibe el ID del producto
     * @return el stock del producto
     *//*

    @Override
    public Optional<StockProducto> getStockByProducto(int codigo_producto) {
        return stockProductoCrudRep.findStockProductoByCodigoProducto(codigo_producto);
    }

    */
/**
     * Obtener solo las unidades de venta que maneja el producto
     * @param codigo_producto : codigo del producto
     * @return un listado con las unidades que maneja y las que no
     *//*

    @Override
    public unidadesStockDTO getUnidadesStock(int codigo_producto) {
        unidadesStockDTO unidadesStock = new unidadesStockDTO();
        Optional<StockProducto> stock = getStockByProducto(codigo_producto);
        if(stock.isEmpty()) {
            return null;
        }

        if (stock.get().getVendeKg() == true) {
            unidadesStock.setVendeKg("kg");
        } else {
            unidadesStock.setVendeKg(stock.get().getVendeKg().toString());
        }
        if (stock.get().getVendeLb() == true) {
            unidadesStock.setVendeLb("lb");
        } else {
            unidadesStock.setVendeLb(stock.get().getVendeLb().toString());
        }
        if (stock.get().getVendeArroba() == true) {
            unidadesStock.setVendeArroba("bulto");
        } else {
            unidadesStock.setVendeArroba(stock.get().getVendeArroba().toString());
        }

        return unidadesStock;
    }

    //POSTs---------

    */
/**
     * Crear un nuevo stock de un producto
     *
     * @param stock : recibe el stock a guardar
     * @return el stock guardado
     *//*

    @Override
    public boolean postNewStockProducto(StockProducto stock) {
        try {
            stockProductoCrudRep.saveAndFlush(stock);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    //PUTs-PATCHs----------

    */
/**
     * Actualiza el stock de un producto
     * @param p_code : recibe el ID del producto
     * @param stock : recibe el stock con los datos a actualizar
     * @return el stock actualizado
    *//*

    public StockProducto upDateStockProducto(int p_code, stockProductosUpdateDTO stock) {
        return null;
    }

    //DELETEs----------

    @Override
    public boolean deleteStockProducto(int codigo_producto) {
        Optional<StockProducto> stockProducto = stockProductoCrudRep.findStockProductoByCodigoProducto(codigo_producto);
        if (stockProducto.isEmpty()) {
            return false;
        }
        stockProductoCrudRep.delete(stockProducto.get());
        return true;

    }

}

*/

