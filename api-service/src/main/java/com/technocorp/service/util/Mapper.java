package com.technocorp.service.util;

import com.technocorp.persistence.model.line.Line;
import com.technocorp.persistence.model.dto.LineDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Function;

public class Mapper {

    private Mapper() {
    }

    public static final Function<Line, LineDTO> findAllLineDTO =
            line -> Optional.ofNullable(LineDTO.builder()
                    .name(line.getName())
                    .code(line.getCode())
                    .build())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    public static final Function<Line, LineDTO> toLineDTO =
            line -> Optional.ofNullable(LineDTO.builder()
                    .name(line.getName())
                    .code(line.getCode())
                    .itinerary(line.getItinerary())
                    .build())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));


    public static final Function<LineDTO, Line> toLine =
            lineDTO -> Optional.ofNullable(Line.builder()
                    .name(lineDTO.getName())
                    .code(lineDTO.getCode())
                    .itinerary(lineDTO.getItinerary())
                    .build())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

/*    public static final Function<List<Line>, List<BusStop>> toBusStop =
            lineList -> lineList.stream()
                    .map(line -> BusStop.builder()
                            .lat())*/
}
