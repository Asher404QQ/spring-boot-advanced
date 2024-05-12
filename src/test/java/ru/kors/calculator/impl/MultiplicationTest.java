package ru.kors.calculator.impl;

import org.junit.jupiter.api.Test;
import ru.kors.calculator.operations.impl.Multiplication;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplicationTest {
    private final Multiplication operation = new Multiplication();

    @Test
    public void shouldMatchSign() {
        assertTrue(operation.handles("*"));
        assertFalse(operation.handles("-"));
    }

    @Test
    public void shouldCorrectlyApply() {
        assertEquals(8, operation.apply(2, 4));
        assertNotEquals(5, operation.apply(2, 3));
    }
}
