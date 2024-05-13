package ru.kors.webmvc.service;

import ru.kors.webmvc.model.Book;

import java.util.Optional;

public interface BookService {
    Iterable<Book> findAll();
    Book save(Book book);
    Optional<Book> findByISBN(String isbn);
}
