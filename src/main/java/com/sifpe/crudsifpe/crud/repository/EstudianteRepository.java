package com.sifpe.crudsifpe.crud.repository;

import com.sifpe.crudsifpe.crud.entity.Estudiante;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EstudianteRepository extends MongoRepository<Estudiante,Integer> {
    boolean existsByNombre(String nombre);
    Optional<Estudiante> findByNombre(String nombre);
    Optional<Estudiante>findByDocumento(String documento);

}
