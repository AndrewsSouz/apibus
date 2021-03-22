package com.technocorp.service;

import com.technocorp.persistence.model.dto.LineControllerDTO;
import com.technocorp.persistence.model.dto.LineServiceDTO;

import java.util.List;

public interface LineService {

    List<LineControllerDTO> findAll();

    List<LineControllerDTO> findByName(String name);

    LineControllerDTO findByCode(String code);

    void save(LineServiceDTO line);

    void update(String id, LineServiceDTO line);

    void delete(String id);

}
