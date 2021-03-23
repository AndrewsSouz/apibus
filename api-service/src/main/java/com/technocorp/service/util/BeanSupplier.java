package com.technocorp.service.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanSupplier {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public StringBuilder stringBuilder() {
        return new StringBuilder(
                "https://api.mapbox.com/geocoding/v5/mapbox.places/"
        );
    }
}
