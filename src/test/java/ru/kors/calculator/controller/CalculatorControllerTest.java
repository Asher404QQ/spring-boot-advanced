package ru.kors.calculator.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kors.calculator.Calculator;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Calculator calculator;

    @Test
    public void shouldSuccessOperation() throws Exception {
        Mockito.when(calculator.calculate(4, 8, "*")).thenReturn(32);

        var request = MockMvcRequestBuilders.get("/calculate")
                .param("lhs", "4")
                .param("rhs", "8")
                .param("op", "*");
        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("32"));

        Mockito.verify(calculator, Mockito.times(1)).calculate(4, 8, "*");
    }

}