package ru.kors.dataaccess.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kors.dataaccess.model.Customer;
import ru.kors.dataaccess.repository.CustomerRepository;

@Component
public class CustomerLister implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(CustomerLister.class);
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository repository;

    public CustomerLister(JdbcTemplate jdbcTemplate, CustomerRepository repository) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        jdbcTemplate.query("SELECT id, name, email FROM customer",
//                rs -> {
//                    logger.info("Customer [id={}, name={}, email={}]", rs.getString("id"),
//                            rs.getString("name"), rs.getString("email"));
//                });
        var customer = new Customer("Check Checkov", "check@email.com");
//        repository.save(customer);
        repository.findAll().forEach(c -> logger.info("{}", c));
        repository.removeById(7L);
    }
}
