package com.technocorp.persistence.model.line;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("lines")
public class LineBson {
    private String id;
    @Id
    @JsonProperty("codigo")
    private String code;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("itinerario")
    private org.bson.Document itinerary;
}