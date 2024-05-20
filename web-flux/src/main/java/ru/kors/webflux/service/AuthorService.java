package ru.kors.webflux.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kors.webflux.models.Author;

public interface AuthorService {
    Flux<Author> findAll();
    Mono<Author> save(Author author);
    Mono<Author> findById(Long id);
}
