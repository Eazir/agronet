/*
package com.tyrservices.agronetb.Services.ProductosService;

import com.campofrescobackend.modelos.DTOs.productoUpdateDTO;
import com.campofrescobackend.modelos.DTOs.stockProductosUpdateDTO;
import com.campofrescobackend.modelos.DTOs.unidadesStockDTO;
import com.campofrescobackend.modelos.entidades.Producto;
import com.campofrescobackend.modelos.entidades.StockProducto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    //Seccion de los servicios de productos

    List<Producto> getAllProducto();
    List<Producto> getAllByProductor(int idProductor);
    List<Producto> getAllByCategoria(int idCategoria);
    List<Producto> getAllByNombreProducto(String nombreProducto);
    boolean postNewProducto(Producto producto);
    Producto upDateProducto(int p_code, productoUpdateDTO producto);
    boolean deleteProducto(int p_code);

    //Seccion de los servicios de stock

    List<StockProducto> getAllStock();
    Optional<StockProducto> getStockByProducto(int p_code);
    unidadesStockDTO getUnidadesStock(int p_code);
    boolean postNewStockProducto(StockProducto stockProducto);
    StockProducto upDateStockProducto(int p_code, stockProductosUpdateDTO stockProducto);
    boolean deleteStockProducto(int p_code);
}
*/
