package ru.kors.webmvc.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AsyncHelloWorldController.class)
class AsyncHelloWorldControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRunInAsyncMode() throws Exception {
        var mvcResult = mockMvc.perform(get("/api/v1/async/hello"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(Matchers.startsWith("Async Hello World! Spring Boot Version: ")));
    }
}