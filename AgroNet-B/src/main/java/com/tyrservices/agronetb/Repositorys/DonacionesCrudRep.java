package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.Donaciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonacionesCrudRep extends JpaRepository<Donaciones, Long> {
}
