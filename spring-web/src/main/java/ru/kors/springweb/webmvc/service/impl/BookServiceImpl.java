package ru.kors.springweb.webmvc.service.impl;

import org.springframework.stereotype.Service;
import ru.kors.springweb.webmvc.model.Book;
import ru.kors.springweb.webmvc.service.BookService;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookServiceImpl implements BookService {
    private final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Book save(Book book) {
        return books.put(book.isbn(), book);
    }

    @Override
    public Optional<Book> findByISBN(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }
}
