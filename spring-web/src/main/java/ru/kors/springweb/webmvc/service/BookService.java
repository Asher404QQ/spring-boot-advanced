package ru.kors.springweb.webmvc.service;


import ru.kors.springweb.webmvc.model.Book;

import java.util.Optional;

public interface BookService {
    Iterable<Book> findAll();
    Book save(Book book);
    Optional<Book> findByISBN(String isbn);
}
