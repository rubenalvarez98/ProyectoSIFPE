package com.sifpe.crudsifpe.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "psicologico")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Psicologico {
    @Id
    private int id;
    private Estudiante estudiante;
    private String tiposeguimiento;

    private String descripcion;
    private String estadoseguimiento;
    private String fechaseguimiento;


}
