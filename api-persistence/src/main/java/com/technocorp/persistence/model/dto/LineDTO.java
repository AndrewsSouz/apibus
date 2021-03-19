package com.technocorp.persistence.model.dto;

import com.technocorp.persistence.model.line.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineDTO {
    private String code;
    private String name;
    private List<Coordinate> itinerary;
}