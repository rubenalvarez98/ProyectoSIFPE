/**package com.sifpe.crudsifpe.crud;

import com.sifpe.crudsifpe.crud.entity.ERole;
import com.sifpe.crudsifpe.crud.entity.Role;
import com.sifpe.crudsifpe.crud.entity.User;
import com.sifpe.crudsifpe.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SpringSecurityJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class);
    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository cuntomuserRepository;

    @Bean
    CommandLineRunner init() {
        return args -> {


            User userEntity1 = User.builder()
                    .username("Angie")
                    .email("angiebetancurt64@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .roles(Set.of(Role.builder()
                            .name(ERole.valueOf(ERole.DIRECTORA.name()))
                            .build()))
                    .build();
            cuntomuserRepository.save(userEntity1);
        };
    }
}*/
