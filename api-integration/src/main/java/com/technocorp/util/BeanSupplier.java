package com.technocorp.util;

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
                "http://api.positionstack.com/v1/forward?access_key=889412e83ce965edbe4cfceae802fd0a&query="
        );
    }
}
