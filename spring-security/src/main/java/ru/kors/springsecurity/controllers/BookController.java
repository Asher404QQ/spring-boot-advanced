package ru.kors.springsecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kors.springsecurity.model.Book;
import ru.kors.springsecurity.service.BookService;

@Controller
@RequestMapping("/api/v1/security/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/library.html")
    public String all(Model model) {
        model.addAttribute("books", service.findAll());
        return "books/list";
    }

    @GetMapping(value = "/library.html", params = "isbn")
    public String get(@RequestParam String isbn, Model model) {
        service.findByISBN(isbn).ifPresent(book -> model.addAttribute("book", book));
        return "books/details";
    }

    @PostMapping("/save")
    @ResponseBody public ResponseEntity<Book> save(@RequestBody Book book,
                                                   UriComponentsBuilder builder) {
        return ResponseEntity.created(
                builder.path("/api/v1/books/{isbn}").build(book.isbn())
        ).body(service.save(book));
    }

    @GetMapping("/{isbn}")
    @ResponseBody public ResponseEntity<Book> findByISBN(@PathVariable String isbn) {
        return ResponseEntity.of(service.findByISBN(isbn));
    }

    @GetMapping
    @ResponseBody public Iterable<Book> findAll() {
        return service.findAll();
    }

    @GetMapping("/server-error")
    public void error() {
        var cause = new NullPointerException("Test Exception");
        throw new ServerErrorException(cause.getMessage(), cause);
    }
}
