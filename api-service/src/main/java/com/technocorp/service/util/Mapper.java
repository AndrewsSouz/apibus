package com.technocorp.service.util;

import com.technocorp.persistence.model.dto.LineServiceDTO;
import com.technocorp.persistence.model.line.Line;
import com.technocorp.persistence.model.dto.LineControllerDTO;
import com.technocorp.persistence.model.line.LineBson;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.technocorp.service.util.StringMessages.UNEXPECTED_ERROR;

public class Mapper {

    private Mapper() {
    }

    public static final Function<List<LineBson>, List<LineControllerDTO>> toListLineControllerDTO =
            lineBson -> lineBson.stream()
                    .map(line -> Optional.ofNullable(LineControllerDTO.builder()
                            .name(line.getName())
                            .code(line.getCode())
                            .build())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, UNEXPECTED_ERROR))
                    ).collect(Collectors.toList());

    public static final Function<List<LineBson>, List<LineControllerDTO>> toListLineControllerDtoWithItinerary =
            lineBson -> lineBson.stream()
                    .map(line -> Optional.ofNullable(LineControllerDTO.builder()
                            .name(line.getName())
                            .code(line.getCode())
                            .docItinerary(line.getItinerary())
                            .build())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, UNEXPECTED_ERROR))
                    ).collect(Collectors.toList());


    public static final Function<LineBson, LineControllerDTO> toLineControllerDTO =
            line -> Optional.ofNullable(LineControllerDTO.builder()
                    .name(line.getName())
                    .code(line.getCode())
                    .docItinerary(line.getItinerary())
                    .build())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, UNEXPECTED_ERROR));

    public static final Function<LineControllerDTO, LineServiceDTO> toLineServiceDTO =
            line -> Optional.ofNullable(LineServiceDTO.builder()
                    .name(line.getName())
                    .code(line.getCode())
                    .itinerary(line.getGeoItinerary())
                    .build())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, UNEXPECTED_ERROR));


    public static final Function<LineServiceDTO, Line> toLine =
            lineServiceDTO -> Optional.ofNullable(Line.builder()
                    .name(lineServiceDTO.getName())
                    .code(lineServiceDTO.getCode())
                    .itinerary(lineServiceDTO.getItinerary())
                    .build())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, UNEXPECTED_ERROR));




}
