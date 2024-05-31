package ru.kors.dataaccess.repository;

import ru.kors.dataaccess.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
    void removeById(Long id);
}
