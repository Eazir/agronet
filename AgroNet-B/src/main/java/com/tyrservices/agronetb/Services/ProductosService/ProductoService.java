
package com.tyrservices.agronetb.Services.ProductosService;


import com.tyrservices.agronetb.Models.DTOs.productoUpdateDTO;
import com.tyrservices.agronetb.Models.DTOs.stockProductosUpdateDTO;
import com.tyrservices.agronetb.Models.DTOs.unidadesStockDTO;
import com.tyrservices.agronetb.Models.entidades.Producto;
import com.tyrservices.agronetb.Models.entidades.StockProducto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {


    // Métodos Producto
    List<Producto> getAllProducto();
    List<Producto> getAllByProductor(Long idProductor);
    List<Producto> getAllByCategoria(Long idCategoria);
    List<Producto> getAllByNombreProducto(String nombre);
    Optional<Producto> getProductoById(Long id);
    boolean postNewProducto(Producto producto);
    Producto upDateProducto(Long codigo_producto, Producto producto);
    boolean deleteProducto(Long codigo_producto);

    // Métodos Stock
    List<StockProducto> getAllStock();
    Optional<StockProducto> getStockByProducto(Long codigo_producto);
    boolean postNewStockProducto(StockProducto stock);
    void actualizarStockProducto(StockProducto stock);
    boolean deleteStockProducto(Long codigo_producto);
}

