package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProductoCrudRep extends JpaRepository<CategoriaProducto, Long> {
}
