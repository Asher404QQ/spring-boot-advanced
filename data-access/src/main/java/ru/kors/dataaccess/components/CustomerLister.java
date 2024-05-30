package ru.kors.dataaccess.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CustomerLister implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(CustomerLister.class);
    private final DataSource dataSource;

    public CustomerLister(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var sql = "SELECT id, name, email FROM customer";
        try (var con = dataSource.getConnection()) {
            var sttm = con.createStatement();
            var rs = sttm.executeQuery(sql);
            while (rs.next()) {
                logger.info("Customer [id={}, name={}, email={}]", rs.getString("id"),
                        rs.getString("name"), rs.getString("email"));
            }
        }
    }
}
