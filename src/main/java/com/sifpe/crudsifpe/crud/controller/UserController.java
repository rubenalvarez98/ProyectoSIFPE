package com.sifpe.crudsifpe.crud.controller;

import com.sifpe.crudsifpe.crud.dto.UserDto;
import com.sifpe.crudsifpe.crud.entity.User;
import com.sifpe.crudsifpe.crud.service.UserService;
import com.sifpe.crudsifpe.global.exepctions.AttributeException;
import com.sifpe.crudsifpe.global.exepctions.ResourseNotFundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto userDto) {
        try {
            Authentication authentication;
        } catch (Exception e) {

        }
        return ResponseEntity.ok("sdfsadfas");
    }

    @PostMapping("/createUser")
    @PreAuthorize("hasRole('DIRECTORA'")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto)
            throws ResourseNotFundException, AttributeException {
        User user = userService.save(userDto);

        if (user != null) {
            return ResponseEntity.ok("Usuario creado exitosamante");
        }

        return ResponseEntity.internalServerError().body("Error al crear usuario");
    }
}
