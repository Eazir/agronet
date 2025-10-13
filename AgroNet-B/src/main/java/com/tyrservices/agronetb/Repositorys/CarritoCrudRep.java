package com.tyrservices.agronetb.Repositorys;


import com.tyrservices.agronetb.Models.entidades.Carrito;
import com.tyrservices.agronetb.Models.entidades.UsuarioConsumidor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoCrudRep extends JpaRepository<Carrito, Long> {

    Optional<Carrito> findCarritoByDocConsumidor (UsuarioConsumidor docConsumidor);
}
