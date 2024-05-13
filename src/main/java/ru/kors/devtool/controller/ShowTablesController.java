package ru.kors.devtool.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShowTablesController {
    private final JdbcTemplate jdbcTemplate;

    public ShowTablesController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/show-tables")
    public List<String> showTables() {
        String sql = "select tablename from pg_catalog.pg_tables";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
