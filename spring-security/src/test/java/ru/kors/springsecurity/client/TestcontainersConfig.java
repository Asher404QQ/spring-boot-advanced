package ru.kors.springsecurity.client;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public interface TestcontainersConfig {
    @Container
    @ServiceConnection
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2");
}
