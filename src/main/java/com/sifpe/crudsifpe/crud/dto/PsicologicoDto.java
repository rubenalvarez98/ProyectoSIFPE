package com.sifpe.crudsifpe.crud.dto;

import com.sifpe.crudsifpe.crud.entity.Estudiante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PsicologicoDto {


    @NotBlank(message = "El tipo de seguimiento es obligatorio")
    private String tiposeguimiento;
    @NotNull(message = "El estudiante es obligatorio")

    private Estudiante estudiante;
    private String descripcion;
    private String estadoseguimiento;
    private String fechaseguimiento;

}


