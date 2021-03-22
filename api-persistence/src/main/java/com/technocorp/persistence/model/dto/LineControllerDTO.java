package com.technocorp.persistence.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("LineControllerDTO: Bus line characteristics")
public class LineControllerDTO {
    @ApiModelProperty("Prefix of the line")
    private String code;
    @ApiModelProperty("Name of the line")
    private String name;
    @ApiModelProperty("Response itinerary attribute")
    private Document docItinerary;
    @ApiModelProperty("Request of the line")
    private GeoJsonMultiPoint geoItinerary;
}