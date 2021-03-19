package com.technocorp;

import com.technocorp.persistence.model.line.Line;
import com.technocorp.persistence.repository.LineRepository;
import com.technocorp.service.serviceimpl.IntegrationServiceImpl;
import com.technocorp.service.serviceimpl.LineServiceImpl;
import com.technocorp.util.BeanSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        IntegrationServiceImpl.class,
        BeanSupplier.class,
        LineServiceImpl.class,
        })
//@Disabled
class IntegrationServiceImplTests {

    private Line line;

    @Autowired
    private IntegrationServiceImpl integrationServiceImpl;

    @MockBean
    LineRepository lineRepository;

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
        var response = this.integrationServiceImpl.callAllLines();
        System.out.println(response);
        assertEquals(line, response.get(0));
    }

    @Test
    void shouldReturnALine(){
        var response = integrationServiceImpl.callLine("5472");
        System.out.println(response);
        assertEquals("",response);
    }
    @Test
    void shouldReturnAnAddressCoordinate() throws UnsupportedEncodingException {
        var response = integrationServiceImpl.searchAddress("rua mali");
        System.out.println(response);
        assertEquals("",response);
    }

}
