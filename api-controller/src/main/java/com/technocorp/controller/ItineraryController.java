package com.technocorp.controller;

import com.technocorp.persistence.model.Coordinate;
import com.technocorp.persistence.service.serviceimpl.ItineraryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/itinerary")
public class ItineraryController {

    ItineraryServiceImpl itineraryService;

    @GetMapping
    @ResponseStatus(OK)
    public List<Coordinate> findByCode(@RequestParam String code) {
        return itineraryService.findByCode(code);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public List<Coordinate> save(@RequestParam String code,
                                 @RequestBody Coordinate coordinate) {
        return itineraryService.save(code, coordinate);
    }

    @PutMapping
    @ResponseStatus(OK)
    public List<Coordinate> update(@RequestParam String code,
                                   @RequestBody Coordinate coordinate) {
        return itineraryService.update(code, coordinate);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void delete(@RequestParam String code,
                       @RequestBody Coordinate coordinate) {
        itineraryService.delete(code, coordinate);
    }


}
