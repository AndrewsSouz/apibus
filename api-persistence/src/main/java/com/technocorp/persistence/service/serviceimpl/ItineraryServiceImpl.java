package com.technocorp.persistence.service.serviceimpl;

import com.technocorp.persistence.model.Coordinate;
import com.technocorp.persistence.service.ItineraryService;
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

    public List<Coordinate> findByCode(String code) {
        return lineService.findByCode(code).getItinerary();
    }

    public List<Coordinate> save(String code, Coordinate coordinate) {
        if (!lineService.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NOT_FOUND);
        }
        var line = lineService.findByCode(code);

        if (line.getItinerary().isEmpty()) {
            addCoordinateToItinerary(line.getItinerary(), coordinate);
            return lineService.update(line);
        } else if (line.getItinerary().stream()
                .anyMatch(coord -> coord.equals(coordinate))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ALREADY_EXISTS);
        } else if (line.getItinerary().stream().anyMatch(
                coord -> coord.getLat().equals(coordinate.getLat()))) {
            line.getItinerary().stream()
                    .filter(coord -> coord.getLat().equals(coordinate.getLat()))
                    .forEach(coord -> coord.setLng(coordinate.getLng()));
            return lineService.update(line);
        } else if (line.getItinerary().stream().anyMatch(
                coord -> coord.getLng().equals(coordinate.getLng()))) {
            line.getItinerary().stream()
                    .filter(coord -> coord.getLng().equals(coordinate.getLng()))
                    .forEach(coord -> coord.setLat(coordinate.getLat()));
            return lineService.update(line);
        }
        line.getItinerary().add(coordinate);
        return lineService.update(line);
    }


    public List<Coordinate> update(String code, Coordinate coordinate) {
        return save(code, coordinate);
    }

    public void delete(String code, Coordinate coordinate) {
        if (!lineService.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NOT_FOUND);
        }
        var line = lineService.findByCode(code);
        line.getItinerary().remove(coordinate);
        lineService.update(line);
    }

    public void addCoordinateToItinerary(
            List<Coordinate> itinerary, Coordinate coordinate) {
        itinerary.add(coordinate);
    }

}
