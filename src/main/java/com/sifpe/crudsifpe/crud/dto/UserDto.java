package com.sifpe.crudsifpe.crud.dto;

import com.sifpe.crudsifpe.crud.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String username;

    @Email
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "La constraseña es obligatoria")
    private String password;

    //@NotBlank(message = "rol es obligatorio")
    private Set<Role> roles;

}
