package com.sifpe.crudsifpe.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "estudiante")
public class Estudiante {
    @Id
    private int id;
    private String nombre;
    private  int edad;
    private String sexo;
    private String tipodocumento;
    private String documento;
    private String fechanacimiento;


}
