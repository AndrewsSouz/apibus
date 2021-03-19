package com.technocorp.service.serviceimpl;

import com.technocorp.persistence.model.address.AddressCoordinateWrapper;
import com.technocorp.persistence.model.dto.LineDTO;
import com.technocorp.persistence.model.line.Line;
import com.technocorp.persistence.model.line.Coordinate;
import com.technocorp.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
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
public class IntegrationServiceImpl {

    private final RestTemplate restTemplate;
    private final StringBuilder builder;
    private final LineServiceImpl lineService;


    @Autowired
    public IntegrationServiceImpl(RestTemplate restTemplate, StringBuilder builder, LineServiceImpl lineService) {
        this.restTemplate = restTemplate;
        this.builder = builder;
        this.lineService = lineService;
        this.restTemplate.setMessageConverters(Config.messageConverter.get());
    }


    public List<LineDTO> findLinesByAddressRange(String address, Distance distance) throws UnsupportedEncodingException {
        var coordinates = searchAddress(address).getAddressCoordinates();
        return lineService.findInCoordinateInRadius(coordinates[0],distance);
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
                .itinerary((List<Coordinate>) list.stream()
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
        return restTemplate.getForObject(
                uri, AddressCoordinateWrapper.class);
    }

    public List<Line> findLineByName(String name) {
        return callAllLines()
                .stream()
                .filter(line -> StringUtils.containsIgnoreCase(line.getName(), name))
                .collect(Collectors.toList());
    }

    public List<Line> findLineByPrefix(String prefix) {
        return callAllLines()
                .stream()
                .filter(line -> StringUtils.containsIgnoreCase(line.getCode(), prefix))
                .collect(Collectors.toList());
    }

}
