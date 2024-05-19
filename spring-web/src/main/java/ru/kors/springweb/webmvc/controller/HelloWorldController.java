package ru.kors.springweb.webmvc.controller;

import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String hello() {
        String version = SpringBootVersion.getVersion();
        return String.format("Hello World! Spring Boot version %s", version);
    }
}
