package ru.kors.springweb.calculator.operations.impl;

import org.springframework.stereotype.Component;
import ru.kors.springweb.calculator.operations.Operation;

@Component
public class Addition implements Operation {
    @Override
    public int apply(int lhs, int rhs) {
        return lhs + rhs;
    }

    @Override
    public boolean handles(String op) {
        return "+".equals(op);
    }
}
