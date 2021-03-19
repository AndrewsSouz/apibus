package com.technocorp.controller;

import com.technocorp.integration.IntegrationService;
import com.technocorp.persistence.model.Line;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/integration/line")
public class IntegrationController {

    private final IntegrationService integrationService;

    @GetMapping
    @ResponseStatus(OK)
    public List<Line> find(@RequestParam(required = false) String id) {
        if (Objects.nonNull(id)) {
            return Collections.singletonList(integrationService.callLine(id));
        }
        return integrationService.callAllLines();
    }

}
