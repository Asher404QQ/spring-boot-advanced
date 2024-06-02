package ru.kors.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kors.dataaccess.model.CustomerDTO;

public interface CustomerDAO extends JpaRepository<CustomerDTO, Long> {
}
