package com.sifpe.crudsifpe.crud.repository;

import com.sifpe.crudsifpe.crud.entity.Psicologico;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PsicologicoRepository extends MongoRepository<Psicologico,Integer> {
    boolean existsByTiposeguimiento(String tiposeguimiento);

    Optional<Psicologico>findByTiposeguimiento(String tiposeguimiento);
}
