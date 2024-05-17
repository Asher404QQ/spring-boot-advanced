package ru.kors.webmvc.model;

import java.util.List;
import java.util.Objects;

public class ProxyAuthor {
    private String name;
    private String email;
    private List<Post> posts;

    public ProxyAuthor(String name, String email, List<Post> posts) {
        this.name = name;
        this.email = email;
        this.posts = posts;
    }

    public ProxyAuthor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProxyAuthor that = (ProxyAuthor) o;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(posts, that.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, posts);
    }

    @Override
    public String toString() {
        return "ProxyAuthor{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", posts=" + posts +
                '}';
    }
}
