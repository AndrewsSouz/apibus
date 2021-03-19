package com.technocorp.controller;

import com.technocorp.persistence.model.StopCoordinate;
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
    public List<StopCoordinate> findByCode(@RequestParam String code) {
        return itineraryService.findByCode(code);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public List<StopCoordinate> save(@RequestParam String code,
                                     @RequestBody StopCoordinate stopCoordinate) {
        return itineraryService.save(code, stopCoordinate);
    }

    @PutMapping
    @ResponseStatus(OK)
    public List<StopCoordinate> update(@RequestParam String code,
                                       @RequestBody StopCoordinate stopCoordinate) {
        return itineraryService.update(code, stopCoordinate);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void delete(@RequestParam String code,
                       @RequestBody StopCoordinate stopCoordinate) {
        itineraryService.delete(code, stopCoordinate);
    }


}
