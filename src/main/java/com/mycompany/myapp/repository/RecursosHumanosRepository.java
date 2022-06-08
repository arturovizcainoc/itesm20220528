package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RecursosHumanos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the RecursosHumanos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecursosHumanosRepository extends MongoRepository<RecursosHumanos, String> {}
