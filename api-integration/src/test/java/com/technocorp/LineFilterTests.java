package com.technocorp;

import com.technocorp.integration.IntegrationService;
import com.technocorp.linefilter.LineFilter;
import com.technocorp.util.BeanSupplier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LineFilter.class, IntegrationService.class, BeanSupplier.class})
@Disabled
public class LineFilterTests {

    @Autowired
    LineFilter lineFilter;


    @Test
    void shouldReturnLinesThatMatchName(){
        var response = lineFilter.findLineByName("assis");
        System.out.println(response);
        assertEquals("",response);
    }
    @Test
    void shouldReturnLinesThatMatchPrefix(){
        var response = lineFilter.findLineByPrefix("tr61");
        System.out.println(response);
        assertEquals("",response);
    }



}
