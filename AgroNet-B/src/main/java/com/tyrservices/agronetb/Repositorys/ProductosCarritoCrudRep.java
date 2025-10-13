package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.Carrito;
import com.tyrservices.agronetb.Models.entidades.ProductoCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductosCarritoCrudRep extends JpaRepository<ProductoCarrito, Long> {
    List<ProductoCarrito> findAllByCodigoCarrito(Carrito codigoCarrito);
}
