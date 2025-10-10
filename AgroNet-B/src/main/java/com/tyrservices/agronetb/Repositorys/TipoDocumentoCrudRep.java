package com.tyrservices.agronetb.Repositorys;

import com.tyrservices.agronetb.Models.entidades.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDocumentoCrudRep extends JpaRepository<TipoDocumento, Long> {
}
