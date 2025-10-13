package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoCrudRep extends JpaRepository<Pedido, Long> {
}
