package com.technocorp.persistence.repository;

import com.technocorp.persistence.model.Line;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LineRepository extends MongoRepository<Line,String> {

    List<Line> findByNameIgnoreCaseContaining(String name);
    Line findByCodeIgnoreCase(String code);
    boolean existsByCodeIgnoreCase(String code);
    void deleteByCode(String code);

}
