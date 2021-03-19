package com.technocorp.controller;


import com.technocorp.persistence.model.Line;
import com.technocorp.persistence.service.serviceimpl.LineServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/line")
public class LineController {

    private final LineServiceImpl lineService;

    @GetMapping
    @ResponseStatus(OK)
    public List<Line> find(@RequestParam(required = false) String name,
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
