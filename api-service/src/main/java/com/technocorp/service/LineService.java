package com.technocorp.service;

import com.technocorp.persistence.model.dto.LineControllerDTO;
import com.technocorp.persistence.model.dto.LineServiceDTO;

import java.util.List;

public interface LineService {

    List<LineControllerDTO> find(String name, String code);

    void save(LineServiceDTO line);

    void update(String id, LineServiceDTO line);

    void delete(String id);

}
