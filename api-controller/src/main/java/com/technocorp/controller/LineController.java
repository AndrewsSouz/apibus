package com.technocorp.controller;


import com.technocorp.persistence.model.dto.LineControllerDTO;
import com.technocorp.service.serviceimpl.LineServiceImpl;
import com.technocorp.service.util.Mapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/line")
@Api("Line Resource")
@CrossOrigin("*")
public class LineController {

    private final LineServiceImpl lineService;

    @GetMapping
    @ResponseStatus(OK)
    @ApiOperation("List all lines stored on database if parameters are not present, if present search lines by name or code")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "If the request has been succeeded"),
            @ApiResponse(code = 400, message = "If given two params, a code or a name that not exist")})
    public List<LineControllerDTO> find(@ApiParam("The name line to be find, return a list of lines that match the param")
                                        @RequestParam(required = false) String name,
                                        @ApiParam("The code of line te find, return a single line")
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
    @ApiOperation("Given an address and a distance range, list all lines that are inside the given range")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "If the request succeed"),
            @ApiResponse(code = 400, message = "If distance are null")})
    public List<LineControllerDTO> findByRange(
            @ApiParam("The address to be the central point")
            @RequestParam(name = "lat", required = false) Double lat,
            @ApiParam("The address to be the central point")
            @RequestParam(name = "lng", required = false) Double lng,
            @ApiParam("The distance to be the radius of search")
            @RequestParam(name = "distance", required = false) Double distance){
        if (Objects.isNull(distance)) {
            throw new ResponseStatusException(BAD_REQUEST, "Distance must be setted");
        }
        return lineService.findLinesByAddressRange(lat,lng, distance);
    }

    @PostMapping
    @ResponseStatus(OK)
    @ApiOperation("Save a line on database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "If the operation succeed"),
            @ApiResponse(code = 409, message = "When the given object already exist on database"),
            @ApiResponse(code = 400, message = "When the given object is null")})
    public void save(@RequestBody LineControllerDTO line) {
        lineService.save(Mapper.toLineServiceDTO.apply(line));
    }

    @PutMapping
    @ResponseStatus(OK)
    @ApiOperation("Update a line on the database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "If the operation succeed"),
            @ApiResponse(code = 409, message = "When the given object already exist on database"),
            @ApiResponse(code = 400, message = "When the given object is null")})
    public void update(String id, LineControllerDTO line) {
        lineService.update(id,
                Mapper.toLineServiceDTO.apply(line));
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Delete a line on database")
    @ApiResponse(code = 204, message = "Every time the endpoint is called")
    public void delete(@RequestParam String code) {
        lineService.delete(code);
    }


}
