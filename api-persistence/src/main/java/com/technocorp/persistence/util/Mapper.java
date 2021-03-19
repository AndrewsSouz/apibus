package com.technocorp.persistence.util;

import com.technocorp.persistence.model.Line;
import com.technocorp.persistence.model.dto.LineDTO;

import java.util.function.Function;

public class Mapper {

    private Mapper(){}

    public static final  Function<Line, LineDTO> findAllLineDTO =
            line -> LineDTO.builder()
                    .name(line.getName())
                    .code(line.getCode())
                    .build();

    public static final  Function<Line, LineDTO> toLineDTO =
            line -> LineDTO.builder()
                    .name(line.getName())
                    .code(line.getCode())
                    .itinerary(line.getItinerary())
                    .build();


    public static final  Function<LineDTO,Line> toLine =
            lineDTO -> Line.builder()
                    .name(lineDTO.getName())
                    .code(lineDTO.getCode())
                    .itinerary(lineDTO.getItinerary())
                    .build();
}
