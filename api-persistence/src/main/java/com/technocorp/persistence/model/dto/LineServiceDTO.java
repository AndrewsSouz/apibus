package com.technocorp.persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineServiceDTO {
    private String code;
    private String name;
    private GeoJsonMultiPoint itinerary;
}