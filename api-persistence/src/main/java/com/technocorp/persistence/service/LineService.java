package com.technocorp.persistence.service;

import com.technocorp.persistence.model.Line;

import java.util.List;

public interface LineService {

    List<Line> findAll();

    List<Line> findByName(String name);

    Line findByCode(String code);

    Line save(Line line);

    Line update(String id, Line line);

    void delete(String id);


}
