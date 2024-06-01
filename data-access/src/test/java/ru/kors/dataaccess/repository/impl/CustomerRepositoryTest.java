package ru.kors.dataaccess.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kors.dataaccess.model.Customer;
import ru.kors.dataaccess.repository.impl.CustomerRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(properties = "spring.flyway.enabled=false")
@Import(CustomerRepositoryImpl.class)
@Testcontainers
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepositoryImpl repository;
    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2");

    @Test
    void shouldInsertCustomer() {
        assertEquals(List.of(), repository.findAll());

        var newCustomer = new Customer("Testing", "test@email.com");
        var customer = repository.save(newCustomer);

        assertNotNull(customer.id());
        assertEquals("Testing", customer.name());
        assertEquals("test@email.com", customer.email());

        assertEquals(Optional.of(customer), repository.findById(customer.id()));
    }

    @Test
    void shouldFindAll() {
        assertEquals(List.of(), repository.findAll());

        repository.save(new Customer("Testing", "test@email.com"));
        repository.save(new Customer("Testing1", "test1@email.com"));

        assertEquals(2, repository.findAll().size());
    }
}