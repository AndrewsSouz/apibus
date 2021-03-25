package com.technocorp;

import com.technocorp.persistence.model.dto.LineControllerDTO;
import com.technocorp.persistence.model.dto.LineServiceDTO;
import com.technocorp.persistence.model.line.Line;
import com.technocorp.persistence.model.line.LineBson;
import com.technocorp.persistence.repository.LineBsonRepository;
import com.technocorp.persistence.repository.LineRepository;
import com.technocorp.service.serviceimpl.LineServiceImpl;
import com.technocorp.service.util.Mapper;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LineServiceTests {

    Line line;
    LineBson lineBson;
    LineServiceDTO lineServiceDTO;
    LineControllerDTO lineControllerDTO;

    @Mock
    LineRepository lineRepository;

    @Mock
    LineBsonRepository lineBsonRepository;

    @InjectMocks
    LineServiceImpl lineService;

    @BeforeEach
    void setUp() {
        this.lineBson = LineBson.builder()
                .id("1")
                .code("637-1")
                .name("Chacara das Pedras")
                .itinerary(new Document("Key", new GeoJsonMultiPoint(
                        List.of(new Point(-30.1213, -51.1312),
                                new Point(-30.4567, -52.6789)))))
                .build();

        this.line = Line.builder()
                .code("637-1")
                .name("Chacara das Pedras")
                .itinerary(new GeoJsonMultiPoint(
                        List.of(new Point(-30.1213, -51.1312),
                                new Point(-30.4567, -52.6789))))
                .build();

        this.lineControllerDTO = LineControllerDTO.builder()
                .code(this.lineBson.getCode())
                .name(this.lineBson.getName())
                .docItinerary(this.lineBson.getItinerary())
                .build();

        this.lineServiceDTO = LineServiceDTO.builder()
                .code(this.lineBson.getCode())
                .name(this.lineBson.getName())
                .itinerary(this.line.getItinerary())
                .build();
    }


    @Test
    void shouldReturnAListOfLines() {
        this.lineControllerDTO.setDocItinerary(null);
        when(lineBsonRepository.findAll()).thenReturn(Collections.singletonList(this.lineBson));
        var stubActual = lineService.find(null, null);
        var stubExpected =
                Collections.singletonList(lineControllerDTO);
        assertEquals(stubExpected, stubActual);
    }

    @Test
    void shouldReturnALineWhenFindByName() {
        this.lineControllerDTO.setDocItinerary(null);
        when(lineBsonRepository.findByNameIgnoreCaseContaining(this.line.getName()))
                .thenReturn(Collections.singletonList(this.lineBson));
        var stubActual = lineService.find(this.line.getName(), null);
        var stubExpected = Collections.singletonList(this.lineControllerDTO);
        assertEquals(stubExpected, stubActual);
    }


    @Test
    void shouldReturnALineWhenFindByCode() {
        when(lineBsonRepository.findByCodeIgnoreCase(this.line.getCode()))
                .thenReturn(this.lineBson);
        var stubActual = lineService.find(null, this.line.getCode());
        var stubExpected =
                Collections.singletonList(this.lineControllerDTO);
        assertEquals(stubExpected, stubActual);
    }

    @Test
    void shouldReturnStatusNotFoundWhenFindByCode() {
        when(lineBsonRepository.findByCodeIgnoreCase(this.line.getCode()))
                .thenReturn(null);

        var thrown = assertThrows(ResponseStatusException.class,
                () -> lineService.find(null, "637-1"));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());

    }

    @Test
    void shouldSaveALine() {
        when(lineBsonRepository.existsByCodeIgnoreCase(line.getCode()))
                .thenReturn(false);

        lineService.save(Mapper.fromLineToServiceDTO.apply(this.line));
        verify(lineRepository, times(1)).save(this.line);
    }

    @Test
    void shouldReturnStatusConflictWhenSave() {
        when(lineBsonRepository.existsByCodeIgnoreCase(line.getCode()))
                .thenReturn(true);
        var thrown = assertThrows(ResponseStatusException.class,
                () -> lineService.save(this.lineServiceDTO));
        assertEquals(HttpStatus.CONFLICT, thrown.getStatus());
    }

    @Test
    void shouldUpdateALine() {
        when(lineBsonRepository.existsByCodeIgnoreCase(line.getCode()))
                .thenReturn(true);

        lineService.update(this.line.getCode(),
                Mapper.fromLineToServiceDTO.apply(this.line));
        verify(lineRepository, times(1)).save(this.line);
    }

    @Test
    void shouldDeleteALine() {
        lineService.delete(this.line.getCode());
        verify(lineRepository, times(1))
                .deleteByCode(this.line.getCode());
    }

}
