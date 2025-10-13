package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.UsuarioConsumidor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioConsumidorCrudRep extends JpaRepository<UsuarioConsumidor, Long> {
    boolean existsByEmail(String email);

    boolean existsByNombreUsuario(String nombreUsuario);


    boolean existsByDocConsumidor(Long docProductor);

    Optional<UsuarioConsumidor> findByEmail(String email);
}
