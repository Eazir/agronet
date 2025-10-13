package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.UsuarioProductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioProductorCrudRep extends JpaRepository<UsuarioProductor, Long> {

    boolean existsByEmail(String email);

    boolean existsByNombreUsuario(String nombreUsuario);

    boolean existsByDocProductor(Long docProductor);

    Optional<UsuarioProductor> findByEmail(String email);
}
