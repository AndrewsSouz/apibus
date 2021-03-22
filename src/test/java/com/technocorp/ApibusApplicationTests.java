package com.technocorp;

import com.technocorp.controller.LineController;
import com.technocorp.persistence.model.line.LineBson;
import com.technocorp.persistence.repository.LineBsonRepository;
import com.technocorp.persistence.repository.LineRepository;
import com.technocorp.service.serviceimpl.LineServiceImpl;
import com.technocorp.service.util.BeanSupplier;
import com.technocorp.service.util.Mapper;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {LineController.class, LineServiceImpl.class, BeanSupplier.class})
class ApibusApplicationTests {


    LineBson line;

    @MockBean
    LineBsonRepository lineBsonRepository;

    @MockBean
    LineRepository lineRepository;

    @Autowired
    LineController lineController;


    @BeforeEach
    void setUp() {
        this.line = LineBson.builder()
                .code("631-1")
                .name("Parque dos Mayas")
                .itinerary(new Document("Key", new GeoJsonMultiPoint(
                        List.of(new Point(-30.1213, -51.1312),
                                new Point(-30.4567, -52.6789)))))
                .build();
    }

    @Test
    void shouldReturnAListOfLines() {
        this.line.setItinerary(null);
        when(lineBsonRepository.findAll()).thenReturn(Collections.singletonList(this.line));
        var stubActual = lineController.find(null, null);
        var stubExpected =
                Collections.singletonList(Mapper.toLineControllerDTO.apply(this.line));
        assertEquals(stubExpected, stubActual);
    }

    @Test
    void whenFindShouldReturnStatusBadRequest() {
        var thrown = assertThrows(
                ResponseStatusException.class,
                () -> lineController.find("null", "null"));
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
    }


}
