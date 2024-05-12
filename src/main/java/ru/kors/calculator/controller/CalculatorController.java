package ru.kors.calculator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.kors.calculator.Calculator;

@RestController
public class CalculatorController {
    private final Calculator calculator;

    public CalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }

    @GetMapping("/calculate")
    public int calculate(Calculation calculation) {
        return calculator.calculate(calculation.lhs, calculation.rhs, calculation.op);
    }

    record Calculation(int lhs, int rhs, String op){}
}
