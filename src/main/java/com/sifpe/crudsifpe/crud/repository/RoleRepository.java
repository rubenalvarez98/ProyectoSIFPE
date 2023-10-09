package com.sifpe.crudsifpe.crud.repository;

import com.sifpe.crudsifpe.crud.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
