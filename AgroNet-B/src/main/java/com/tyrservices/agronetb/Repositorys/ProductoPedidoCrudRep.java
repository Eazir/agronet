package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.ProductoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoPedidoCrudRep extends JpaRepository<ProductoPedido, Long> {
}
