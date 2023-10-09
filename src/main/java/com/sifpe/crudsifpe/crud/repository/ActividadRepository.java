package com.sifpe.crudsifpe.crud.repository;

import com.sifpe.crudsifpe.crud.entity.Actividad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActividadRepository extends MongoRepository<Actividad,Integer> {
    Boolean existsByNombre(String nombre);

    Optional<Actividad> findByNombre(String nombre);
}