package ru.kors.springsecurity.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(value = HelloWorldController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@WebMvcTest(HelloWorldController.class)
@WithMockUser
class HelloWorldControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSayHello() throws Exception{
        mockMvc.perform(get("/api/v1/security/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.startsWith("Hello World! Spring Boot Version: ")));
    }
}