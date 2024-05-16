package ru.kors.webmvc.controller;

import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.kors.webmvc.model.Author;
import ru.kors.webmvc.repository.AuthorRepository;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/v1/async/authors")
public class AuthorController {
    private final AuthorRepository repository;
    private final TaskExecutor executor;

    public AuthorController(AuthorRepository repository, TaskExecutor executor) {
        this.repository = repository;
        this.executor = executor;
    }

    @PostMapping
    public Author save(@RequestBody Author author) {
        return repository.save(author);
    }

    @GetMapping
//    public ResponseBodyEmitter findAll() {
//        var emmiter = new ResponseBodyEmitter();
      public SseEmitter findAll() {
        var emmiter = new SseEmitter();
        executor.execute(() -> {
            var authors = repository.findAll();
            try {
                for (Author author : authors) {
                    sendAndDelay(emmiter, author);
                }
                emmiter.complete();
            } catch (InterruptedException | IOException e) {
                emmiter.completeWithError(e);
            }
        });
        return emmiter;
    }

    private void sendAndDelay(ResponseBodyEmitter emitter, Author author)
            throws IOException, InterruptedException {
        var eventBuilder = SseEmitter.event();
//        emitter.send(author, MediaType.APPLICATION_JSON);
        emitter.send(eventBuilder.data(author, MediaType.APPLICATION_JSON)
                .name("author-created")
                .id(String.valueOf(author.hashCode())).build());
        Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
    }
}
