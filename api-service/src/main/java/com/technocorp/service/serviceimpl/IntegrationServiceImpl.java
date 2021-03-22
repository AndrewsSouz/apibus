package com.technocorp.service.serviceimpl;

import com.technocorp.persistence.model.address.AddressCoordinateWrapper;
import com.technocorp.persistence.model.line.Line;
import com.technocorp.persistence.repository.LineRepository;
import com.technocorp.service.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;


@Service
public class IntegrationServiceImpl {

    private static final String ALL_LINES = "http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o";
    private static final String LINE = "http://www.poatransporte.com.br/php/facades/process.php?a=il&p=";

    private static final String CITY = URLEncoder.encode(", Porto Alegre, RS, Brasil", StandardCharsets.UTF_8)
            .replace("+", "%20");

    private static final String TOKEN =
            "?access_token=pk.eyJ1Ijoic3Rvcm16ZHJ1aWQiLCJhIjoiY2ttanlldXZhMHZ0NTJuczduNjJ5dDE1biJ9.Rb4f1P4z_XkkEad1JZch1Q";

    private final RestTemplate restTemplate;
    private final StringBuilder builder;
    private final LineRepository lineRepository;


    @Autowired
    public IntegrationServiceImpl(RestTemplate restTemplate,
                                  StringBuilder builder,
                                  LineRepository lineRepository) {
        this.restTemplate = restTemplate;
        this.lineRepository = lineRepository;
        this.builder = builder;
        this.restTemplate.setMessageConverters(Config.messageConverter.get());
    }

    public void saveAllLines() {
        var allLines = callAllLines();
        Objects.requireNonNull(allLines);
        allLines.stream()
                .filter(line -> !lineRepository.existsByCodeIgnoreCase(line.getCode()))
                .forEach(
                        line -> {
                                lineRepository.save(callLine(line.getId()));
                            try {
                                sleep(60);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                );
    }


    public List<Line> callAllLines() {
        return Arrays.asList(Optional.ofNullable(
                restTemplate.getForObject(ALL_LINES, Line[].class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY)));
    }

    public Line callLine(String id) {
        var map = restTemplate.getForObject(LINE + id, Map.class);
        Objects.requireNonNull(map);

        List<LinkedHashMap<String, String>> list =
                (ArrayList<LinkedHashMap<String, String>>)
                        map.values().stream()
                                .filter(index -> index.toString().contains("lat"))
                                .collect(Collectors.toList());

        GeoJsonMultiPoint points = new GeoJsonMultiPoint(list.stream()
                .map(point -> (new Point(
                        Double.parseDouble(point.get("lat")),
                        Double.parseDouble(point.get("lng"))
                ))).collect(Collectors.toList()));

        var save = Line.builder()
                .id(map.get("idlinha").toString())
                .code(map.get("codigo").toString())
                .name(map.get("nome").toString())
                .itinerary(points)
                .build();
        System.out.println(save);
        return save;
    }

    public AddressCoordinateWrapper searchAddress(String address) throws URISyntaxException {
        builder.append(URLEncoder.encode(address, StandardCharsets.UTF_8)
                .replace("+", "%20"));
        builder.append(CITY);
        builder.append(".json");
        builder.append(TOKEN);

        URI requestUri = new URI(builder.toString());
        return restTemplate.getForObject(
                requestUri, AddressCoordinateWrapper.class);
    }

}
