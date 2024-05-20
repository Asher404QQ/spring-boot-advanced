package ru.kors.springsecurity.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.kors.springsecurity.client.TestcontainersConfig;
import ru.kors.springsecurity.model.Book;
import ru.kors.springsecurity.service.BookService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.security.user.password=admin")
@AutoConfigureWebTestClient
class BookControllerTest implements TestcontainersConfig {
    @Autowired
    private WebTestClient client;
    @MockBean
    private BookService bookService;
    private final Map<String, Book> bookMap = new HashMap<>();

    @BeforeAll
    static void init() {
        postgres.start();
    }

    @BeforeEach
    void setUp() {
        bookMap.clear();

        Book book1 = new Book("123", "Мастер и Маргарита", List.of("Михаил Булгаков"));
        Book book2 = new Book("321", "Преступление и наказание", List.of("Федор Михайлович Достоевский"));
        Book book3 = new Book("213", "Маленький принц", List.of("Антуан де Сент-Экзюпери"));

        bookMap.put(book1.isbn(), book1);
        bookMap.put(book2.isbn(), book2);
        bookMap.put(book3.isbn(), book3);
    }

    @Test
    void shouldReturnAllBooks() {
        Mockito.when(bookService.findAll()).thenReturn(bookMap.values());

        client.get().uri("/api/v1/books")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("user", "admin"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class).hasSize(3);
    }
}