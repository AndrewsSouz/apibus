package com.technocorp.persistence.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("lines")
public class Line {
    private String id;
    @JsonProperty("codigo")
    private String code;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("itinerario")
    private List<Coordinate> itinerary;
}
