package ru.kors.springweb.calculator.operations;

public interface Operation {
    int apply(int lhs, int rhs);
    boolean handles(String op);
}
