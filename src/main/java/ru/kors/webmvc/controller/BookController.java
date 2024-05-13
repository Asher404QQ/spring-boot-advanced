package ru.kors.webmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kors.webmvc.model.Book;
import ru.kors.webmvc.service.BookService;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Book> save(@RequestBody Book book,
                                     UriComponentsBuilder builder) {
        return ResponseEntity.created(
                builder.path("/api/v1/books/{isbn}").build(book.isbn())
        ).body(service.save(book));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> findByISBN(@PathVariable String isbn) {
        return ResponseEntity.of(service.findByISBN(isbn));
    }

    @GetMapping
    public Iterable<Book> findAll() {
        return service.findAll();
    }
}
