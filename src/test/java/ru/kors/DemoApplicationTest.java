package ru.kors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import ru.kors.calculator.Calculator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTest {
    @Autowired
    private Calculator calculator;


    @Test
    public void shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculate(10, 32, "/"));
    }

    @Test
    public void shouldApplyMultiplicationOperation(CapturedOutput capturedOutput) {
        calculator.calculate(10, 32, "*");
        assertTrue(capturedOutput.getOut().contains("10 * 32 = 320"));
    }
}