package com.technocorp.service;

import com.technocorp.persistence.model.line.Coordinate;

import java.util.List;

public interface ItineraryService {

    List<Coordinate> findByCode(String code);

    List<Coordinate> save(String code, Coordinate coordinate);

    List<Coordinate> update(String code, Coordinate coordinate);

    void delete(String code, Coordinate coordinate);


}
