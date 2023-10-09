package com.sifpe.crudsifpe.crud.entity;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Document(collection = "roles")
public class Role {
    @Id
    private int id;
    @JsonEnumDefaultValue
    private ERole name;

    public Role(String name) {
        this.name = ERole.valueOf(name); // Convierte la cadena en un valor de ERole
    }
}
