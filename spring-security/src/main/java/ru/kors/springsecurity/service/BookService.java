package ru.kors.springsecurity.service;



import ru.kors.springsecurity.model.Book;

import java.util.Optional;

public interface BookService {
    Iterable<Book> findAll();
    Book save(Book book);
    Optional<Book> findByISBN(String isbn);
}
