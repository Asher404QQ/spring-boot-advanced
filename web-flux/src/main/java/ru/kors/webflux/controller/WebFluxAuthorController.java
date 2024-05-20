package ru.kors.webflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kors.webflux.models.Author;
import ru.kors.webflux.service.AuthorService;

@RestController
@RequestMapping("/api/v1/authors")
public class WebFluxAuthorController {
    private final AuthorService service;

    public WebFluxAuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Author> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Author> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Mono<ResponseEntity<Author>> save(@RequestBody Mono<Author> author,
                                             UriComponentsBuilder builder) {
        var created = author.flatMap(service::save);
        return created.map(it -> createResponse(it, builder));
    }

    private ResponseEntity<Author> createResponse(Author author, UriComponentsBuilder builder) {
        var newAuthorURI = builder.path("/api/v1/web-flux/authors/{id}").build(author.getId());
        return ResponseEntity.created(newAuthorURI).body(author);
    }
}
