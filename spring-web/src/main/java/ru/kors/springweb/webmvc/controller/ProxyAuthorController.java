package ru.kors.springweb.webmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kors.springweb.webmvc.model.Author;
import ru.kors.springweb.webmvc.model.ProxyAuthor;
import ru.kors.springweb.webmvc.service.ProxyAuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class ProxyAuthorController {
    private final ProxyAuthorService service;

    public ProxyAuthorController(ProxyAuthorService service) {
        this.service = service;
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ProxyAuthor> findByIdv2(@PathVariable Long id) {
        return service.findByIdv2(id);
    }

    @GetMapping()
    public List<Author> authors() {
        return service.authors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProxyAuthor> findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
