package ru.kors.dataaccess.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import ru.kors.dataaccess.model.Customer;
import ru.kors.dataaccess.model.CustomerDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = "spring.flyway.enabled=false")
class CustomerDAOTest {
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private TestEntityManager manager;

    @Test
    void shouldInsertCustomer() {
        assertEquals(List.of(), customerDAO.findAll());

        var newCustomer = new CustomerDTO("Testing", "test@email.com");
        var customer = customerDAO.save(newCustomer);

        assertEquals(1, customerDAO.findAll().size());
        assertTrue(customer.getId() > -1L);
        assertEquals("Testing", customer.getName());
        assertEquals("test@email.com", customer.getEmail());

        assertEquals(Optional.of(customer), customerDAO.findById(customer.getId()));
    }

    @Test
    void shouldFindAll() {
        assertEquals(List.of(), customerDAO.findAll());

        customerDAO.save(new CustomerDTO("Testing", "test@email.com"));
        customerDAO.save(new CustomerDTO("Testing1", "test1@email.com"));

        assertEquals(2, customerDAO.findAll().size());
    }
}