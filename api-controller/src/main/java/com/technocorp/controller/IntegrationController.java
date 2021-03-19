package com.technocorp.controller;

import com.technocorp.integration.IntegrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/integration")
public class IntegrationController {

    private final IntegrationService integrationService;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void integrateData() {
        integrationService.saveAllLines();
    }



}
