package ru.kors.springweb.webmvc.service;

import org.springframework.http.ResponseEntity;
import ru.kors.springweb.webmvc.model.Author;
import ru.kors.springweb.webmvc.model.ProxyAuthor;

import java.util.List;

public interface ProxyAuthorService {
    List<Author> authors();
    ResponseEntity<ProxyAuthor> findById(Long id);
    ResponseEntity<ProxyAuthor> findByIdv2(Long id);
}
