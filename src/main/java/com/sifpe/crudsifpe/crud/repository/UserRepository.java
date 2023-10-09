package com.sifpe.crudsifpe.crud.repository;

import com.sifpe.crudsifpe.crud.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByUsername(String userName);

    boolean existsByUsername(String username);

}
