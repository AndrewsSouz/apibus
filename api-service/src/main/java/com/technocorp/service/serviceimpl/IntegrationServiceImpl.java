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

import static com.technocorp.service.util.StringMessages.UNEXPECTED_ERROR;
import static com.technocorp.service.util.UriStrings.*;
import static java.lang.Thread.sleep;


@Service
public class IntegrationServiceImpl {



    private final RestTemplate restTemplate;
    private final StringBuilder builder;
    private final LineRepository lineRepository;

    @Autowired
    public IntegrationServiceImpl(RestTemplate restTemplate,
                                  StringBuilder builder,
                                  LineRepository lineRepository) {
        this.lineRepository = lineRepository;
        this.builder = builder;
        this.restTemplate = restTemplate;
        this.restTemplate.setMessageConverters(Config.messageConverter.get());
    }

    /**
     * Retrieve all bus lines from the
     * external api and store on database
      */
    public void saveAllLines() {
        var allLines = callAllLines();
        allLines.stream()
                .filter(line -> !lineRepository.existsByCodeIgnoreCase(line.getCode()))
                .forEach(
                        line -> {
                                lineRepository.save(callLine(line.getId()));
                            try {
                                sleep(60);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                );
    }


    /**
     * Request all bus lines for the endpoint
     * @return A list of all lines without itinerary
     */
    private List<Line> callAllLines() {
        return Arrays.asList(Optional.ofNullable(
                restTemplate.getForObject(ALL_LINES, Line[].class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY,UNEXPECTED_ERROR)));
    }

    /**
     * Build the line object to be stored
     * @param id Comes from saveAllLines()
     * @return The line built with itinerary
     */
    private Line callLine(String id) {
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

        System.out.println(map.get("nome"));

        return Line.builder()
                .id(map.get("idlinha").toString())
                .code(map.get("codigo").toString())
                .name(map.get("nome").toString())
                .itinerary(points)
                .build();
    }

    /**
     * Method used to search by address,
     * retrieve a coordinate from an external api
     * to pass for findLinesByAddressRange()
     * @param address The given address to retrieve the coordinates
     * @return Nested Object with coordinates
     * @throws URISyntaxException If an error happen when construct the URI
     */
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
