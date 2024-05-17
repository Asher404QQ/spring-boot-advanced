package ru.kors.webmvc.client;

import org.springframework.web.service.annotation.GetExchange;
import ru.kors.webmvc.model.Post;

import java.util.List;

public interface PostClient {
    @GetExchange("https://jsonplaceholder.typicode.com/posts")
    List<Post> getPosts();
}
