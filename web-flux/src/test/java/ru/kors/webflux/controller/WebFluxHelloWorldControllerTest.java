package ru.kors.webflux.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(WebFluxHelloWorldController.class)
class WebFluxHelloWorldControllerTest {
    @Autowired
    private WebTestClient client;

    @Test
    void shouldReturnHello() {
        client.get().uri("/api/v1/hello").exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(Matchers.startsWith("Hello World! Spring Boot Version: "));
    }
}