package com.technocorp.persistence.service.serviceimpl;

import com.technocorp.persistence.model.StopCoordinate;
import com.technocorp.persistence.model.Line;
import com.technocorp.persistence.repository.LineRepository;
import com.technocorp.persistence.service.LineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.technocorp.persistence.util.StringMessages.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;


@AllArgsConstructor
@Service
public class LineServiceImpl implements LineService {


    private final LineRepository lineRepository;

    public List<Line> findAll() {
        return lineRepository.findAll();
    }


    public List<Line> findByName(String name) {
        Objects.requireNonNull(name, NULL_CODE_MSG);
        return lineRepository.findByNameIgnoreCaseContaining(name);
    }

    public Line findByCode(String code) {
        Objects.requireNonNull(code, NULL_CODE_MSG);
        return lineRepository.findByCodeIgnoreCase(code);
    }

    public Line save(Line line) {
        Objects.requireNonNull(line);
        if (lineRepository.existsByCodeIgnoreCase(line.getCode()) &&
                lineRepository.findByCodeIgnoreCase(line.getCode())
                        .getName().equals(line.getName())) {
            throw new ResponseStatusException(CONFLICT, ALREADY_EXISTS);
        }
        if(Objects.isNull(line.getItinerary())){
            line.setItinerary(Collections.emptyList());
        }
        return lineRepository.save(line);
    }


    public Line update(String code, Line line) {
        Objects.requireNonNull(code, NULL_CODE_MSG);
        Objects.requireNonNull(line);
        if(!lineRepository.existsByCodeIgnoreCase(code)){
            throw new ResponseStatusException(BAD_REQUEST,NOT_FOUND);
        }
        return lineRepository.save(line);
    }

    public List<StopCoordinate> update(Line line){
        return lineRepository.save(line).getItinerary();
    }


    public void delete(String code) {
        Objects.requireNonNull(code, NULL_CODE_MSG);
        lineRepository.deleteByCode(code);
    }

    public boolean existsByCode(String code) {
        Objects.requireNonNull(code, NULL_CODE_MSG);
        return lineRepository.existsByCodeIgnoreCase(code);
    }


}
