package ru.kors.dataaccess.model;

public record Customer(Long id, String name, String email) {
    public Customer(String name, String email) {
        this(null, name, email);
    }
}
