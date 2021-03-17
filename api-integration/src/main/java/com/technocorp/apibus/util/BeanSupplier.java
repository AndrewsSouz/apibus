package com.technocorp.apibus.util;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class BeanSupplier {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
