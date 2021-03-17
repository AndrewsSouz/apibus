package com.technocorp.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineItinerary {
    @JsonProperty("idlinha")
    private String id;
    @JsonProperty("codigo")
    private String code;
    @JsonProperty("nome")
    private String name;
    private List<Coordinate> coordinates;
}
