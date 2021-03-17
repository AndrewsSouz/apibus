package com.technocorp.apibus.util;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Config {

    private Config(){}

    public static final Supplier<List<HttpMessageConverter<?>>> messageConverter =
            () -> {
                List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
                messageConverters.add(converter);
                return messageConverters;
            };

}
