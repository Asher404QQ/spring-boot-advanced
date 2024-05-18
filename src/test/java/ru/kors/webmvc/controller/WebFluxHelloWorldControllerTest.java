package ru.kors.webmvc.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloWorldController.class)
class WebFluxHelloWorldControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnStringFromHello() throws Exception {
        String expected = "Hello World! Spring Boot version 3.2.5";
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(expected)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }
}