package com.technocorp.persistence.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Line {

    private String id;
    @JsonProperty("codigo")
    private String code;
    @JsonProperty("nome")
    private String name;
}
