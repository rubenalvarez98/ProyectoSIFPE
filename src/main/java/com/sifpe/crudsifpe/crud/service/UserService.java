package com.sifpe.crudsifpe.crud.service;

import com.sifpe.crudsifpe.crud.dto.UserDto;
import com.sifpe.crudsifpe.crud.entity.ERole;
import com.sifpe.crudsifpe.crud.entity.Role;
import com.sifpe.crudsifpe.crud.entity.User;
import com.sifpe.crudsifpe.crud.repository.UserRepository;
import com.sifpe.crudsifpe.global.exepctions.AttributeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User save(UserDto dto) throws AttributeException {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new AttributeException("El Username ya esta en uso");
        }

        Set<Role> roles = dto.getRoles().stream()
                .map(role -> Role.builder()
                        .name(ERole.valueOf(role.getName().name()))
                        .build())
                .collect(Collectors.toSet());

        User userEntity = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .roles(roles)
                .build();
        return userRepository.save(userEntity);
    }
}
