package ru.kors.webmvc.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.kors.webmvc.model.Book;
import ru.kors.webmvc.service.BookService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService service;
    private Map<String, Book> books = new ConcurrentHashMap<>();

    @BeforeEach
    void setup() {
        Book book1 = new Book("123", "Мастер и Маргарита", List.of("Михаил Булгаков"));
        Book book2 = new Book("321", "Преступление и наказание", List.of("Федор Михайлович Достоевский"));
        Book book3 = new Book("213", "Маленький принц", List.of("Антуан де Сент-Экзюпери"));

        books.put(book1.isbn(), book1);
        books.put(book2.isbn(), book2);
        books.put(book3.isbn(), book3);
    }

    @AfterEach
    void cleanup() {
        books = new ConcurrentHashMap<>();
    }

    @Test
    void shouldReturnViewListOfBooks() throws Exception {
        when(service.findAll()).thenReturn(books.values());
        mockMvc.perform(get("/api/v1/books/library.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/list"))
                .andExpect(model().attribute("books", Matchers.hasSize(3)));
    }

    @Test
    void shouldReturnViewBookByISBN() throws Exception{
        String isbn = "123";
        when(service.findByISBN(anyString())).thenReturn(Optional.of(books.get(isbn)));

        mockMvc.perform(get("/api/v1/books/library.html").param("isbn", isbn))
                .andExpect(status().isOk())
                .andExpect(view().name("books/details"))
                .andExpect(model().attribute("book", Matchers.is(books.get(isbn))));
    }

    @Test
    void shouldReturnEmptyViewByNotFoundBook() throws Exception {
        when(service.findByISBN(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/books/library.html").param("isbn", anyString()))
                .andExpect(status().isOk())
                .andExpect(view().name("books/details"))
                .andExpect(model().attributeDoesNotExist("book"));
    }

    @Test
    void shouldReturnAllBooks() throws Exception {
        when(service.findAll()).thenReturn(books.values());

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[*].isbn", Matchers.containsInAnyOrder("123", "321", "213")))
                .andExpect(jsonPath("$[*].title", Matchers.containsInAnyOrder("Мастер и Маргарита",
                        "Преступление и наказание",
                        "Маленький принц")));
    }

    @Test
    void shouldReturnBookByISBN() throws Exception {
        String isbn = "123";
        when(service.findByISBN("123")).thenReturn(Optional.ofNullable(books.get(isbn)));

        mockMvc.perform(get("/api/v1/books/" + isbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn", Matchers.containsString(isbn)))
                .andExpect(jsonPath("$.title", Matchers.containsString("Мастер и Маргарита")));
    }

    @Test
    void shouldReturn404NotFound() throws Exception {
        String isbn = "312";
        when(service.findByISBN(isbn)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/books/" + isbn))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldSaveBook() throws Exception {
        Book book = new Book("312", "Три товарища", List.of("Эрих Мария Ремарк"));
        when(service.save(book)).thenReturn(book);

        mockMvc.perform(post("/api/v1/books/save").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\":\"312\",\"title\":\"Три товарища\",\"authors\":[\"Эрих Мария Ремарк\"]}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/books/312"));
    }
}