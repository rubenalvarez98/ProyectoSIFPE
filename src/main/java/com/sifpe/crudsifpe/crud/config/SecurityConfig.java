package com.sifpe.crudsifpe.crud.config;


import com.sifpe.crudsifpe.crud.config.filters.JwtAuthenticationFilter;
import com.sifpe.crudsifpe.crud.config.filters.JwtAuthorizationFilter;
import com.sifpe.crudsifpe.crud.config.jwt.JwtUtils;
import com.sifpe.crudsifpe.crud.entity.ERole;
import com.sifpe.crudsifpe.crud.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

/*
 * roles: DIRECTORA, PSICOLOGA, ASISTENTE, DOCENTE
 * */
public class SecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    JwtAuthorizationFilter authorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/psicologicos").hasAnyRole(ERole.PSICOLOGA.toString());
                    auth.requestMatchers("/api/psicologico/**").hasAnyRole(ERole.PSICOLOGA.toString());
                    auth.requestMatchers("/api/actividades").hasAnyRole(
                            ERole.DIRECTORA.toString(),
                            ERole.DOCENTE.toString());
                    auth.requestMatchers("/api/actividad/**").hasAnyRole(ERole.DIRECTORA.toString());
                    auth.requestMatchers("/api/estudiantes").hasAnyRole(
                            ERole.DIRECTORA.toString(),
                            ERole.DOCENTE.toString(),
                            ERole.ASISTENTE.toString(),
                            ERole.PSICOLOGA.toString()
                    );
                    auth.requestMatchers("/api/estudiante/{id}").hasAnyRole(
                            ERole.DIRECTORA.toString(),
                            ERole.ASISTENTE.toString(),
                            ERole.DOCENTE.toString()
                    );
                    auth.requestMatchers("/api/estudiante/buscar/{documento}").hasAnyRole(
                            ERole.DIRECTORA.toString(),
                            ERole.ASISTENTE.toString(),
                            ERole.DOCENTE.toString(),
                            ERole.PSICOLOGA.toString()
                    );
                    auth.requestMatchers("/api/estudiante/**").hasAnyRole(
                            ERole.DIRECTORA.toString(),
                            ERole.ASISTENTE.toString()
                    );
                    auth.requestMatchers("/createUser").hasAnyRole(
                            ERole.DIRECTORA.toString()
                    );
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
}

