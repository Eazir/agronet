package com.tyrservices.agronetb.Models.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "tipo_documento")
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_doc")
    private Integer codigoDoc;

    @Column(name = "nombre_documento", nullable = false, length = 100)
    private String nombreDocumento;

    @Column(name = "uso_documento", nullable = false, length = 100)
    private String usoDocumento;
}