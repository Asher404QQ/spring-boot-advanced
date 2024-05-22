package ru.kors.dataaccess.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class TableLister implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(TableLister.class);
    private final DataSource dataSource;

    public TableLister(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try(var connection = dataSource.getConnection();
        var rs = connection.getMetaData().getTables(null, null, "%", null)) {
            while (rs.next()) {
                logger.info("{}", rs.getString(3));
            }
        }
    }
}
