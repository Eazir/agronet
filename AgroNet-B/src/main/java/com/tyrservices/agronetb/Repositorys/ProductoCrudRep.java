package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.CategoriaProducto;
import com.tyrservices.agronetb.Models.entidades.Producto;
import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoCrudRep extends JpaRepository<Producto, Long> {
    List<Producto> findProductosByCodigoCategoria(CategoriaProducto codigoCategoria);
    List<Producto> findProductosByDocProductor(UsuarioProductor docProductor);
    List<Producto> findByNombreProductoContainingIgnoreCase(String nombreProducto);
}
