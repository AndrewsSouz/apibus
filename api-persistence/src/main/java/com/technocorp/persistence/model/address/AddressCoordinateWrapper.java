package com.technocorp.persistence.model.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCoordinateWrapper {
    @JsonProperty("data")
    private AddressCoordinate[] addressCoordinates;
}