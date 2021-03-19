package com.technocorp.persistence.service.serviceimpl;

import com.technocorp.persistence.model.StopCoordinate;
import com.technocorp.persistence.service.ItineraryService;
import com.technocorp.persistence.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.technocorp.persistence.util.StringMessages.ALREADY_EXISTS;
import static com.technocorp.persistence.util.StringMessages.NOT_FOUND;

;

@Service
@AllArgsConstructor
public class ItineraryServiceImpl implements ItineraryService {

    private final LineServiceImpl lineService;

    public List<StopCoordinate> findByCode(String code) {
        return lineService.findByCode(code).getItinerary();
    }

    public List<StopCoordinate> save(String code, StopCoordinate stopCoordinate) {
        if (!lineService.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NOT_FOUND);
        }
        var line = Mapper.toLine.apply(lineService.findByCode(code));

        if (line.getItinerary().isEmpty()) {
            addCoordinateToItinerary(line.getItinerary(), stopCoordinate);
            return lineService.update(line);
        } else if (line.getItinerary().stream()
                .anyMatch(coord -> coord.equals(stopCoordinate))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ALREADY_EXISTS);
        } else if (line.getItinerary().stream().anyMatch(
                coord -> coord.getLat().equals(stopCoordinate.getLat()))) {
            line.getItinerary().stream()
                    .filter(coord -> coord.getLat().equals(stopCoordinate.getLat()))
                    .forEach(coord -> coord.setLng(stopCoordinate.getLng()));
            return lineService.update(line);
        } else if (line.getItinerary().stream().anyMatch(
                coord -> coord.getLng().equals(stopCoordinate.getLng()))) {
            line.getItinerary().stream()
                    .filter(coord -> coord.getLng().equals(stopCoordinate.getLng()))
                    .forEach(coord -> coord.setLat(stopCoordinate.getLat()));
            return lineService.update(line);
        }
        line.getItinerary().add(stopCoordinate);
        return lineService.update(line);
    }


    public List<StopCoordinate> update(String code, StopCoordinate stopCoordinate) {
        return save(code, stopCoordinate);
    }

    public void delete(String code, StopCoordinate stopCoordinate) {
        if (!lineService.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NOT_FOUND);
        }
        var line = Mapper.toLine.apply(lineService.findByCode(code));
        line.getItinerary().remove(stopCoordinate);
        lineService.update(line);
    }

    public void addCoordinateToItinerary(
            List<StopCoordinate> itinerary, StopCoordinate stopCoordinate) {
        itinerary.add(stopCoordinate);
    }

}
