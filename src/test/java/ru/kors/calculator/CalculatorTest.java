package ru.kors.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kors.calculator.operations.Operation;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculatorTest {
    @MockBean
    private Calculator calculator;
    private Operation operation;

    @BeforeEach
    public void setup() {
        operation = mock(Operation.class);
        calculator = new Calculator(Collections.singletonList(operation));
    }


    @Test
    public void shouldCallMultiplicationOperation() {
        when(operation.handles(anyString())).thenReturn(true);
        when(operation.apply(3, 3)).thenReturn(9);

        calculator.calculate(3, 3, "*");
        verify(operation, times(1)).apply(3, 3);
    }

    @Test
    public void shouldThrowIllegalArgumentException() {
        when(operation.handles(anyString())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(3, 3, "/"));
    }
}