package ru.kors.springsecurity.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.kors.springsecurity.model.Book;
import ru.kors.springsecurity.service.BookService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookServiceImpl implements BookService {
    private final Map<String, Book> books = new ConcurrentHashMap<>();

    {
        Book book1 = new Book("123", "Мастер и Маргарита", List.of("Михаил Булгаков"));
        Book book2 = new Book("321", "Преступление и наказание", List.of("Федор Михайлович Достоевский"));
        Book book3 = new Book("213", "Маленький принц", List.of("Антуан де Сент-Экзюпери"));

        books.put(book1.isbn(), book1);
        books.put(book2.isbn(), book2);
        books.put(book3.isbn(), book3);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    @PreAuthorize("@accessChecker.hasLocalAccess(authentication)")
    public Book save(Book book) {
        return books.put(book.isbn(), book);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<Book> findByISBN(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }
}
