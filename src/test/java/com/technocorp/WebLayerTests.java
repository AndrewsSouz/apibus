package com.technocorp;

import com.technocorp.controller.LineController;
import com.technocorp.service.serviceimpl.LineServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LineController.class)
class WebLayerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LineServiceImpl lineService;

    @Test
    void shouldReturnAListOfLines() throws Exception {
        this.mockMvc.perform(get("/line"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Expect conflict between parameters")
    void whenPassTwoParamsInFindExpectStatusBadRequest() throws Exception {
        this.mockMvc.perform(get("/line")
        .param("name","parque")
        .param("code","637-1"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturnAListOfLinesByRange() throws Exception {
        this.mockMvc.perform(get("/line/search")
                .param("lat", "-31.000000")
                .param("lng", "-51.000000")
                .param("distance", "0.2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void WhenFindByRangeExpectStatusBadRequest() throws Exception {
        this.mockMvc.perform(get("/line/search")
                .param("lat", "-31.000000")
                .param("lng", "-51.000000"))
                .andExpect(status().isBadRequest());
    }


}
