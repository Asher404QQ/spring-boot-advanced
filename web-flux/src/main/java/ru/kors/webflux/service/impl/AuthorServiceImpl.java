package ru.kors.webflux.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kors.webflux.models.Author;
import ru.kors.webflux.repository.AuthorRepository;
import ru.kors.webflux.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Author> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    @Override
    public Mono<Author> save(Author author) {
        return Mono.just(repository.save(author));
    }

    @Override
    public Mono<Author> findById(Long id) {
        return Mono.justOrEmpty(repository.findById(id));
    }
}
