package com.technocorp.integration;

import com.technocorp.util.Config;
import com.technocorp.persistence.model.Coordinate;
import com.technocorp.persistence.model.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class IntegrationService {

    private final RestTemplate restTemplate;

    @Autowired
    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.restTemplate.setMessageConverters(Config.messageConverter.get());
    }

    public List<Line> callAllLines() {
        return Arrays.asList(Optional.ofNullable(
                restTemplate.getForObject("http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o", Line[].class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY)));
    }

    public Line callLine(String id) {
        var map = restTemplate.getForObject("http://www.poatransporte.com.br/php/facades/process.php?a=il&p=" + id, Map.class);
        Objects.requireNonNull(map);
        List list = new ArrayList(map.values());  //Can't give a type for the list, the map have more than one type

        return Line.builder()
                .id(map.get("idlinha").toString())
                .code(map.get("codigo").toString())
                .name(map.get("nome").toString())
                .itinerary((List<Coordinate>) list.stream()
                        .filter(index -> index.toString().contains("lat="))
                        .collect(Collectors.toList()))
                .build();


    }

}
