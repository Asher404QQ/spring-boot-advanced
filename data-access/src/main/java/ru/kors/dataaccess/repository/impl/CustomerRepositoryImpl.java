package ru.kors.dataaccess.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.kors.dataaccess.model.Customer;
import ru.kors.dataaccess.repository.CustomerRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private static final String ALL_QUERY = "SELECT id, name, email FROM customer";
    private static final String FIND_BY_ID_QUERY = "SELECT id, name, email FROM customer WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO customer(name, email) VALUES (?, ?) RETURNING id";
    private static final String REMOVE_QUERY = "DELETE FROM customer WHERE id=?";
    private final JdbcTemplate jdbcTemplate;

    public CustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(ALL_QUERY, (rs, rowNum) -> toCustomer(rs));
    }

    @Override
    public Optional<Customer> findById(Long id) {
        try {
            var customer = jdbcTemplate
                    .queryForObject(FIND_BY_ID_QUERY, (rs, rowNum) -> toCustomer(rs));
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Customer save(Customer customer) {
        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
            ps.setString(1, customer.name());
            ps.setString(2, customer.email());
            return ps;
        }, keyHolder);
        var id = keyHolder.getKey().longValue();
        return new Customer(id, customer.name(), customer.email());
    }

    @Override
    public void removeById(Long id) {
        jdbcTemplate.update(REMOVE_QUERY, id);
    }

    private Customer toCustomer(ResultSet resultSet) throws SQLException {
        var id = resultSet.getLong("id");
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        return new Customer(id, name, email);
    }
}
