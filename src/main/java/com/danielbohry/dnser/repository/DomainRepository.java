package com.danielbohry.dnser.repository;

import com.danielbohry.dnser.service.Domain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRepository extends MongoRepository<Domain, String> {

    Optional<Domain> findByName(String name);
    boolean existsByName(String name);

}
