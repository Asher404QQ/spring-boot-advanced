package ru.kors.webflux.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.kors.webflux.models.Author;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
@DirtiesContext(methodMode = AFTER_METHOD)
@Sql(scripts = "classpath:authors.sql")
class WebFluxAuthorControllerTest {
    @Autowired
    private WebTestClient client;
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2");

    @BeforeAll
    static void init() {
        postgres.start();
    }

    @Test
    void shouldReturnAllAuthors() {
        client.get().uri("/api/v1/authors").exchange()
                .expectStatus().isOk()
                .expectBodyList(Author.class).hasSize(3)
                .contains(new Author(1L, "Tamara27", "Jules_Fisher@hotmail.com"),
                        new Author(2L, "Cristal4", "Ford.Prosacco@hotmail.com"),
                        new Author(3L, "Ezequiel.Morar", "Maribel_Barton22@yahoo.com"));
    }

    @Test
    void shouldSaveAndFindAuthor() {
        Author author = new Author(-45L,"Ewald.Stroman12", "Edyth_Christiansen@hotmail.com");

        client.post().uri("/api/v1/authors").bodyValue(author).exchange()
                .expectStatus().isCreated()
                .expectBody(Author.class).isEqualTo(author);

        client.get().uri("/api/v1/authors/{id}", author.getId()).exchange()
                .expectStatus().isOk()
                .expectBody(Author.class).isEqualTo(author);
    }
}