package com.technocorp.persistence.model.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Model to retrieve coordinates from an external api *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCoordinateWrapper {
    @JsonProperty("data")
    private AddressCoordinate[] addressCoordinates;
}
