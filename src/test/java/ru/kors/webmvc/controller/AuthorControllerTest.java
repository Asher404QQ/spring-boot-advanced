package ru.kors.webmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.kors.webmvc.model.Author;
import ru.kors.webmvc.repository.AuthorRepository;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorRepository repository;

    @Test
    public void shouldCreateResponseWriters() throws Exception{
        when(repository.findAll()).thenReturn(List.of(new Author(1L, "First Name", "test1@Email.com")));

        var mvcResult = mockMvc.perform(get("/api/v1/async/authors"))
                .andExpect(request().asyncStarted())
                .andDo(log())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM_VALUE))
                .andExpect(content().string(
                        allOf(
                                containsString("data:{\"id\":1,\"name\":\"First Name\",\"email\":\"test1@Email.com\"}"),
                                containsString("event:author-created"),
                                containsString("id:")
                        )
                ));
    }
}