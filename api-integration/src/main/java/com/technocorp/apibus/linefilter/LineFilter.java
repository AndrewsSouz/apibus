package com.technocorp.apibus.linefilter;

import com.technocorp.apibus.IntegrationService;
import com.technocorp.persistence.model.Line;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LineFilter {

    IntegrationService integrationService;

    public List<Line> findLineByName(String name) {
        return integrationService.callAllLines()
                .stream()
                .filter(line -> StringUtils.containsIgnoreCase(line.getName(), name))
                .collect(Collectors.toList());
    }

    public List<Line> findLineByPrefix(String prefix) {
        return integrationService.callAllLines()
                .stream()
                .filter(line -> StringUtils.containsIgnoreCase(line.getCode(), prefix))
                .collect(Collectors.toList());
    }

}
