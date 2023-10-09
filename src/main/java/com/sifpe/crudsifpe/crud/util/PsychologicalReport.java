package com.sifpe.crudsifpe.crud.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PsychologicalReport {
    private int id;
    private String nombre;
    private String documento;
    private String tiposeguimiento;

    private String descripcion;
    private String estadoseguimiento;
    private String fechaseguimiento;

}
