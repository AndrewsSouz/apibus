package com.technocorp.service.serviceimpl;

import com.technocorp.persistence.model.dto.LineControllerDTO;
import com.technocorp.persistence.model.dto.LineServiceDTO;
import com.technocorp.persistence.model.line.LineBson;
import com.technocorp.persistence.repository.LineBsonRepository;
import com.technocorp.persistence.repository.LineRepository;
import com.technocorp.service.LineService;
import com.technocorp.service.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.technocorp.service.util.StringMessages.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;


@AllArgsConstructor
@Service
public class LineServiceImpl implements LineService {


    private final LineRepository lineRepository;
    private final LineBsonRepository lineBsonRepository;

    public List<LineControllerDTO> find(String name, String code) {
        if (nonNull(name) && nonNull(code)) {
            throw new ResponseStatusException(BAD_REQUEST, "Must pass only one param");
        } else if (isNull(name) && isNull(code)) {
            return Mapper.toListLineControllerDTO.apply(findAll());
        } else if (isNull(code)) {
            return Mapper.toListLineControllerDTO
                    .apply(findByName(name));
        } else {
            return Collections.singletonList(
                    Mapper.toLineControllerDTO.apply(findByCode(code)));
        }
    }

    public List<LineControllerDTO> findLinesByAddressRange(
            double lat, double lng, Double distance) {
        if (Objects.isNull(distance)) {
            throw new ResponseStatusException(BAD_REQUEST, "Distance must be setted");
        }
        return Mapper.toListLineControllerDTO.apply(lineBsonRepository.findByItineraryNear(
                new Point(lat, lng),
                new Distance(distance, Metrics.KILOMETERS)));
    }

    public void save(LineServiceDTO line) {
        Objects.requireNonNull(line);
        if (lineRepository.existsByCodeIgnoreCase(line.getCode()) &&
                lineRepository.findByCodeIgnoreCase(line.getCode())
                        .getName().equals(line.getName())) {
            throw new ResponseStatusException(CONFLICT, ALREADY_EXISTS);
        }

        lineRepository.save(Mapper.toLine.apply(line));
    }

    public void update(String code, LineServiceDTO line) {
        Objects.requireNonNull(code, NULL_CODE_MSG);
        Objects.requireNonNull(line);
        if (!lineRepository.existsByCodeIgnoreCase(code)) {
            throw new ResponseStatusException(BAD_REQUEST, NOT_FOUND);
        }

        lineRepository.save(Mapper.toLine.apply(line));
    }

    public void delete(String code) {
        Objects.requireNonNull(code, NULL_CODE_MSG);
        lineRepository.deleteByCode(code);
    }

    private List<LineBson> findAll() {
        return lineBsonRepository.findAll();
    }

    private List<LineBson> findByName(String name) {
        Objects.requireNonNull(name, NULL_CODE_MSG);
        return lineBsonRepository.findByNameIgnoreCaseContaining(name);
    }

    private LineBson findByCode(String code) {
        Objects.requireNonNull(code, NULL_CODE_MSG);
        return lineBsonRepository.findByCodeIgnoreCase(code);
    }

}
