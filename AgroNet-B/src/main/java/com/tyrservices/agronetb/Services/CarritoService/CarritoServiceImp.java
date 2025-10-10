/*
package com.tyrservices.agronetb.Services.CarritoService;

import com.campofrescobackend.modelos.entidades.Carrito;
import com.campofrescobackend.modelos.entidades.ProductoCarrito;
import com.campofrescobackend.repositorio.CarritoCrudRep;
import com.campofrescobackend.repositorio.ProductosCarritoCrudRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoServiceImp implements CarritoService {

    //implementaciones
    private final CarritoCrudRep carritoCrudRep;
    private final ProductosCarritoCrudRep productosCarritoCrudRep;

    @Autowired
    public CarritoServiceImp(ProductosCarritoCrudRep productosCarritoCrudRep,
                             CarritoCrudRep carritoCrudRep){
        this.productosCarritoCrudRep = productosCarritoCrudRep;
        this.carritoCrudRep = carritoCrudRep;
    }


//Services del Carrito padre--------------------------------------------------------------------------------------------

    //GETs----------

    @Override
    public Optional<Carrito> getCarritoById(int id) {
        return carritoCrudRep.findById(id);
    }

    @Override
    public Optional<Carrito> getCarritoByUsuario(int doc_usuario) {
        return carritoCrudRep.findCarritoByDocConsumidor(doc_usuario);
    }


    //SAVEs----------

    @Override
    public Carrito saveCarrito(Carrito carrito) {
        return null;
    }


    //DELETEs----------

    @Override
    public boolean deleteCarrito(int id) {
        return false;
    }


//Services de los productos del carrito---------------------------------------------------------------------------------

    @Override
    public List<ProductoCarrito> getproductosCarrito(Integer codigo_carrito) {
        return productosCarritoCrudRep.findAllByCodigoCarrito(codigo_carrito);
    }

    @Override
    public boolean saveProductoCarrito(ProductoCarrito productoCarrito) {
        try {
            ProductoCarrito nuevo = productosCarritoCrudRep.saveAndFlush(productoCarrito);
            Carrito carrito =carritoCrudRep.findById(productoCarrito.getCodigoCarrito()).get();
            BigDecimal totalCarrito= ((carrito.getTotal_carrito()).add(nuevo.getSubtotal()));
            carrito.setTotal_carrito(totalCarrito);
            carritoCrudRep.saveAndFlush(carrito);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateProductoCarrito(ProductoCarrito productoCarrito) {
        return false;
    }

    @Override
    public boolean deleteProductoCarrito(int id) {
        try {
            Optional<ProductoCarrito> producto=productosCarritoCrudRep.findById(id);
            if(producto.isEmpty()){
                return false;
            }
            Optional<Carrito>carrito= carritoCrudRep.findById(producto.get().getCodigoCarrito());
            carrito.get().setTotal_carrito(carrito.get().getTotal_carrito().subtract(producto.get().getSubtotal()));
            productosCarritoCrudRep.delete(producto.get());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
*/
