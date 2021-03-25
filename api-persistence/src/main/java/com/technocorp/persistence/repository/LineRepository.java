package com.technocorp.persistence.repository;

import com.technocorp.persistence.model.line.Line;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends MongoRepository<Line, String> {


    boolean existsByCodeIgnoreCase(String code);

    void deleteByCode(String code);
}
