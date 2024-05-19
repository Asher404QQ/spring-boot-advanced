package ru.kors.springweb.calculator;

import org.springframework.stereotype.Component;
import ru.kors.springweb.calculator.operations.Operation;

import java.util.Collection;

@Component
public class Calculator {
    private final Collection<Operation> operations;

    public Calculator(Collection<Operation> operations) {
        this.operations = operations;
    }

    public int calculate(int lhs, int rhs, String op) {
        return operations.stream()
                .filter(operation -> operation.handles(op))
                .map(operation -> operation.apply(lhs, rhs))
                .peek(result -> System.out.printf("%d %s %d = %s%n", lhs, op, rhs, result))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operation: " + op));
    }
}
