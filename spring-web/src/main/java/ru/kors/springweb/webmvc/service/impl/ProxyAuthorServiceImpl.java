package ru.kors.springweb.webmvc.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import ru.kors.springweb.webmvc.client.AuthorClient;
import ru.kors.springweb.webmvc.client.PostClient;
import ru.kors.springweb.webmvc.model.Author;
import ru.kors.springweb.webmvc.model.Post;
import ru.kors.springweb.webmvc.model.ProxyAuthor;
import ru.kors.springweb.webmvc.repository.AuthorRepository;
import ru.kors.springweb.webmvc.service.ProxyAuthorService;

import java.util.List;
import java.util.Objects;

@Service
public class ProxyAuthorServiceImpl implements ProxyAuthorService {
    public static final String PLACEHOLDER_API = "https://jsonplaceholder.typicode.com/posts";
    private final AuthorClient authorClient;
    private final PostClient postClient;
    private final AuthorRepository authorRepository;
    private final RestTemplate restTemplate;

    public ProxyAuthorServiceImpl(AuthorClient authorClient, PostClient postClient, AuthorRepository authorRepository, RestTemplate restTemplate) {
        this.authorClient = authorClient;
        this.postClient = postClient;
        this.authorRepository = authorRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @Override
    public ResponseEntity<ProxyAuthor> findById(Long id) {
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

    @Override
    public ResponseEntity<ProxyAuthor> findByIdv2(Long id) {
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
}
