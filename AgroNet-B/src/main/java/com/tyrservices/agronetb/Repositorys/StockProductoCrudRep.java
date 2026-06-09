package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.StockProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockProductoCrudRep extends JpaRepository<StockProducto, Long> {


    Optional<StockProducto> findStockProductoByCodigoProducto_CodigoProducto(Long codigoProductoCodigoProducto);
}
