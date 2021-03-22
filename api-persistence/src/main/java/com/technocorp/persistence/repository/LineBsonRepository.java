package com.technocorp.persistence.repository;

import com.technocorp.persistence.model.line.LineBson;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineBsonRepository extends MongoRepository<LineBson, String> {

    List<LineBson> findByItineraryNear(Point point, Distance distance);

    List<LineBson> findByNameIgnoreCaseContaining(String name);

    LineBson findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);
}
