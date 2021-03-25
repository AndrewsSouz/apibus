package com.technocorp.controller;

import com.technocorp.service.serviceimpl.IntegrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/integration")
@Api("Integration resource")
@CrossOrigin("*")
public class IntegrationController {

    private final IntegrationService integrationService;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Start the function to copy all data from procempa api public endpoint to database")
    @ApiResponse(code = 200,message = "Return 200 when whole data has copied")
    public void integrateData() {
        integrationService.saveAllLines();
    }



}
