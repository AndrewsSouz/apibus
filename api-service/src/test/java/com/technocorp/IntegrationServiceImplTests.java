package com.technocorp;

import com.technocorp.persistence.model.line.Line;
import com.technocorp.service.serviceimpl.IntegrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntegrationServiceImplTests {

    private Line line;
    private Line[] lineArray;
    private final String ALL_LINES_URI = "http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o";

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    IntegrationServiceImpl integrationService;

    @BeforeEach
    void setUp() {
        this.line = Line.builder()
                .id("1")
                .code("637-1")
                .name("Chacara das Pedras")
                .itinerary(new GeoJsonMultiPoint(
                        List.of(new Point(-30.1213, -51.1312),
                                new Point(-30.4567, -52.6789))))
                .build();
        lineArray = new Line[]{this.line};
    }


    @Test
    void shouldReturnAListOfLines() {
        when(restTemplate.getForObject(ALL_LINES_URI, Line[].class))
                .thenReturn(lineArray);
        var stubActual = integrationService.callAllLines();
        assertEquals(this.line, stubActual.get(0));
    }



}
