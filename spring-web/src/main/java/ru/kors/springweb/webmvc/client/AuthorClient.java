package ru.kors.springweb.webmvc.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import ru.kors.springweb.webmvc.model.Author;

import java.util.List;

@HttpExchange("http://localhost:8080/api/v1/authors")
public interface AuthorClient {
    @GetExchange("")
    List<Author> getAuthors();
}
