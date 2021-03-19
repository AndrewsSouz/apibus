package com.technocorp.persistence.model.busstop;

import com.technocorp.persistence.model.dto.LineDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusStop {
    private String lat;
    private String lng;
    private List<LineDTO> lines;
}
