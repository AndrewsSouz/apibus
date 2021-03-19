package com.technocorp.persistence.service;

import com.technocorp.persistence.model.Line;
import com.technocorp.persistence.model.dto.LineDTO;

import java.util.List;

public interface LineService {

    List<LineDTO> findAll();

    List<LineDTO> findByName(String name);

    LineDTO findByCode(String code);

    Line save(Line line);

    Line update(String id, Line line);

    void delete(String id);


}
