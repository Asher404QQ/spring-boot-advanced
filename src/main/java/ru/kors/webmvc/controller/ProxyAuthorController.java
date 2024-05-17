package ru.kors.webmvc.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import ru.kors.webmvc.client.AuthorClient;
import ru.kors.webmvc.client.PostClient;
import ru.kors.webmvc.model.Author;
import ru.kors.webmvc.model.Post;
import ru.kors.webmvc.model.ProxyAuthor;
import ru.kors.webmvc.repository.AuthorRepository;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/authors")
public class ProxyAuthorController {
    public static final String PLACEHOLDER_API = "https://jsonplaceholder.typicode.com/posts";
    private final AuthorClient authorClient;
    private final PostClient postClient;
    private final AuthorRepository authorRepository;
    private final RestTemplate restTemplate;

    public ProxyAuthorController(AuthorClient authorClient, PostClient postClient, AuthorRepository authorRepository, RestTemplate restTemplate) {
        this.authorClient = authorClient;
        this.postClient = postClient;
        this.authorRepository = authorRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ProxyAuthor> findByIdv2(@PathVariable Long id) {
        try {
            List<Post> posts = postClient.getPosts();
            List<Author> authors = authorClient.getAuthors();
            Author author = authors.stream()
                    .filter(au -> Objects.equals(au.getId(), id))
                    .findFirst()
                    .orElse(null);
            if (author == null) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(new ProxyAuthor(author.getName(), author.getEmail(),
                    posts.stream().filter(p -> Objects.equals(p.getUserId(), id))
                            .toList()));
        } catch (RestClientException e) {
            if (e instanceof RestClientResponseException rex) {
                return ResponseEntity.status(rex.getStatusCode()).build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProxyAuthor> findById(@PathVariable Long id) {
        try {
            Author author = authorRepository.findById(id).orElse(null);
            if (author == null) return ResponseEntity.noContent().build();
            ResponseEntity<List<Post>> postResponse = restTemplate.exchange(PLACEHOLDER_API, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<Post>>() {
                    });
            List<Post> allPosts = postResponse.getBody();
            List<Post> posts = allPosts.stream()
                    .filter(post -> post.getUserId() == id)
                    .toList();
            ProxyAuthor proxyAuthor = new ProxyAuthor(author.getName(), author.getEmail(), posts);
            return ResponseEntity.ok(proxyAuthor);
        } catch (RestClientException e) {
            if (e instanceof RestClientResponseException rex) {
                return ResponseEntity.status(rex.getStatusCode()).build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }
}
