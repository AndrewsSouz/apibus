package com.technocorp.controller;


import com.technocorp.persistence.model.line.Line;
import com.technocorp.persistence.model.dto.LineDTO;
import com.technocorp.service.serviceimpl.IntegrationServiceImpl;
import com.technocorp.service.serviceimpl.LineServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Metrics;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/line")
public class LineController {

    private final LineServiceImpl lineService;
    private final IntegrationServiceImpl integrationServiceImpl;

    @GetMapping
    @ResponseStatus(OK)
    public List<LineDTO> find(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String code) {
        if (StringUtils.hasLength(name) && !StringUtils.hasLength(code)) {
            return lineService.findByName(name);
        } else if (StringUtils.hasLength(code) && !StringUtils.hasLength(name)) {
            return Collections.singletonList(lineService.findByCode(code));
        } else if (StringUtils.hasLength(name) && StringUtils.hasLength(code)) {
            throw new ResponseStatusException(BAD_REQUEST, "Must provide only one search parameter");
        }
        return lineService.findAll();
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    public List<LineDTO> findByRange(
            @RequestParam String address,
            @RequestParam("d") Double distance) throws UnsupportedEncodingException {
        return integrationServiceImpl.findLinesByAddressRange(
                address, new Distance(distance, Metrics.KILOMETERS));
    }

    @PostMapping
    @ResponseStatus(OK)
    public Line save(@RequestBody Line line) {
        return lineService.save(line);
    }

    @PutMapping
    @ResponseStatus(OK)
    public Line update(String id, Line line) {
        return lineService.update(id, line);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void delete(@RequestParam String code) {
        lineService.delete(code);
    }


}
