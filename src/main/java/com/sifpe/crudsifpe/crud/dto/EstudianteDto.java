package com.sifpe.crudsifpe.crud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @Min(value = 4, message = "No puede tener menos de 4 años")
    private int edad;
    @NotBlank(message = "El sexo es obligatorio")
    private String sexo;

    private String tipodocumento;
    private String documento;
    private String fechanacimiento;

}


