package com.technocorp.persistence.repository;

import com.technocorp.persistence.model.line.Line;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LineRepository extends MongoRepository<Line, String> {

    Line findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);

    void deleteByCode(String code);
}
