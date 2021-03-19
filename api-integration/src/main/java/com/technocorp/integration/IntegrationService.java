package com.technocorp.integration;

import com.technocorp.persistence.model.AddressCoordinateWrapper;
import com.technocorp.persistence.service.serviceimpl.LineServiceImpl;
import com.technocorp.util.Config;
import com.technocorp.persistence.model.StopCoordinate;
import com.technocorp.persistence.model.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class IntegrationService {

    private final RestTemplate restTemplate;
    private final StringBuilder builder;
    private final LineServiceImpl lineService;


    @Autowired
    public IntegrationService(RestTemplate restTemplate, StringBuilder builder, LineServiceImpl lineService) {
        this.restTemplate = restTemplate;
        this.builder = builder;
        this.lineService = lineService;
        this.restTemplate.setMessageConverters(Config.messageConverter.get());
    }


    public void saveAllLines() {
        var allLines = callAllLines();
        Objects.requireNonNull(allLines);
        allLines.forEach(
                line -> lineService.save(callLine(line.getId()))
        );
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
                .itinerary((List<StopCoordinate>) list.stream()
                        .filter(index -> index.toString().contains("lat="))
                        .collect(Collectors.toList()))
                .build();
    }

    public AddressCoordinateWrapper searchAddress(String address) throws UnsupportedEncodingException {
        builder.append(URLEncoder.encode(address, StandardCharsets.UTF_8.toString())
                .replace("+", "%20"));
        builder.append(URLEncoder.encode(", Porto Alegre, RS, Brasil", StandardCharsets.UTF_8.toString())
                .replace("+", "%20"));
        var uri = URI.create(builder.toString());
        return Optional.ofNullable(restTemplate.getForObject(
                uri, AddressCoordinateWrapper.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address not found."));
    }

}
