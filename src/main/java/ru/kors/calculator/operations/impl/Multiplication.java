package ru.kors.calculator.operations.impl;

import ru.kors.calculator.operations.Operation;
import org.springframework.stereotype.Component;

@Component
public class Multiplication implements Operation {
    @Override
    public int apply(int lhs, int rhs) {
        return lhs * rhs;
    }

    @Override
    public boolean handles(String op) {
        return "*".equals(op);
    }
}
