package com.technocorp.persistence.service;

import com.technocorp.persistence.model.StopCoordinate;

import java.util.List;

public interface ItineraryService {

    List<StopCoordinate> findByCode(String code);

    List<StopCoordinate> save(String code, StopCoordinate stopCoordinate);

    List<StopCoordinate> update(String code, StopCoordinate stopCoordinate);

    void delete(String code, StopCoordinate stopCoordinate);


}
