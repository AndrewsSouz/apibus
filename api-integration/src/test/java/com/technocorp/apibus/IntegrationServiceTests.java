package com.technocorp.apibus;

import com.technocorp.apibus.util.BeanSupplier;
import com.technocorp.persistence.model.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {IntegrationService.class, BeanSupplier.class})
class IntegrationServiceTests {

    private Line line;

    @Autowired
    private IntegrationService integrationService;

    @BeforeEach
    void setUp() {
        this.line = Line.builder()
                .id("5517")
                .code("250-1")
                .name("1 DE MAIO")
                .build();
    }

    @Test
    void shouldReturnAListOfLines() {
        var response = this.integrationService.callAllLines();
        System.out.println(response);
        assertEquals(line, response.get(0));
    }

    @Test
    void shouldReturnALine(){
        var response = integrationService.callLine("5472");
        System.out.println(response);
        assertEquals("",response);
    }

}
