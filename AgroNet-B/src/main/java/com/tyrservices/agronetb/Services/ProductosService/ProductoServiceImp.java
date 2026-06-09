
package com.tyrservices.agronetb.Services.ProductosService;


import com.tyrservices.agronetb.Models.entidades.Producto;
import com.tyrservices.agronetb.Models.entidades.StockProducto;
import com.tyrservices.agronetb.Repositorys.ProductoCrudRep;
import com.tyrservices.agronetb.Repositorys.StockProductoCrudRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductoServiceImp implements ProductoService {
    private final ProductoCrudRep productoCrudRep;
    private final StockProductoCrudRep stockProductoCrudRep;

    public ProductoServiceImp(
            ProductoCrudRep productoCrudRep,
            StockProductoCrudRep stockProductoCrudRep) {
        this.productoCrudRep = productoCrudRep;
        this.stockProductoCrudRep = stockProductoCrudRep;
    }

    // Métodos para Producto
    @Override
    public List<Producto> getAllProducto() {
        return productoCrudRep.findAll();
    }

    @Override
    public List<Producto> getAllByProductor(Long idProductor) {
        return productoCrudRep.findProductosByDocProductor_DocProductor(idProductor);
    }

    @Override
    public List<Producto> getAllByCategoria(Long idCategoria) {
        return productoCrudRep.findProductosByCodigoCategoria_CodigoCategoria(idCategoria);
    }

    @Override
    public List<Producto> getAllByNombreProducto(String nombre) {
        return productoCrudRep.findByNombreProductoContainingIgnoreCase(nombre);
    }

    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoCrudRep.findById(id);
    }

    @Override
    public boolean postNewProducto(Producto producto) {
        try {
            productoCrudRep.save(producto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Producto upDateProducto(Long codigo_producto, Producto productoActualizado) {
        Optional<Producto> productoExistente = productoCrudRep.findById(codigo_producto);
        if (productoExistente.isEmpty()) {
            return null;
        }

        Producto producto = productoExistente.get();

        // Actualizar campos
        if (productoActualizado.getCodigoCategoria() != null) {
            producto.setCodigoCategoria(productoActualizado.getCodigoCategoria());
        }
        if (productoActualizado.getNombreProducto() != null) {
            producto.setNombreProducto(productoActualizado.getNombreProducto());
        }
        if (productoActualizado.getOrigenProducto() != null) {
            producto.setOrigenProducto(productoActualizado.getOrigenProducto());
        }
        if (productoActualizado.getDescripcion() != null) {
            producto.setDescripcion(productoActualizado.getDescripcion());
        }
        if (productoActualizado.getUrlImagen() != null) {
            producto.setUrlImagen(productoActualizado.getUrlImagen());
        }

        producto.setEstado(productoActualizado.isEstado());

        return productoCrudRep.save(producto);
    }

    @Override
    public boolean deleteProducto(Long codigo_producto) {
        Optional<Producto> producto = productoCrudRep.findById(codigo_producto);
        if (producto.isEmpty()) {
            return false;
        }

        // Eliminar stock primero
        deleteStockProducto(codigo_producto);

        // Eliminar producto
        productoCrudRep.delete(producto.get());
        return true;
    }

    // Métodos para Stock
    @Override
    public List<StockProducto> getAllStock() {
        return stockProductoCrudRep.findAll();
    }

    @Override
    public Optional<StockProducto> getStockByProducto(Long codigo_producto) {
        return stockProductoCrudRep.findStockProductoByCodigoProducto_CodigoProducto(codigo_producto);
    }

    @Override
    public boolean postNewStockProducto(StockProducto stock) {
        try {
            stockProductoCrudRep.save(stock);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void actualizarStockProducto(StockProducto stockActualizado) {
        Optional<StockProducto> stockExistente = stockProductoCrudRep.findStockProductoByCodigoProducto_CodigoProducto(
                stockActualizado.getCodigoProducto().getCodigoProducto()
        );

        if (stockExistente.isPresent()) {
            StockProducto stock = stockExistente.get();
            stock.setPrecioKilo(stockActualizado.getPrecioKilo());
            stock.setCantidadKg(stockActualizado.getCantidadKg());
            stock.setVendeKg(stockActualizado.getVendeKg());
            stock.setVendeLb(stockActualizado.getVendeLb());
            stock.setVendeTonelada(stockActualizado.getVendeTonelada());
            stockProductoCrudRep.save(stock);
        } else {
            stockProductoCrudRep.save(stockActualizado);
        }
    }

    @Override
    public boolean deleteStockProducto(Long codigo_producto) {
        Optional<StockProducto> stockProducto = stockProductoCrudRep.findStockProductoByCodigoProducto_CodigoProducto(codigo_producto);
        if (stockProducto.isEmpty()) {
            return false;
        }
        stockProductoCrudRep.delete(stockProducto.get());
        return true;
    }
}