package ru.kors.springsecurity.controllers;

import org.springframework.boot.SpringBootVersion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/security")
public class HelloWorldController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        String version = SpringBootVersion.getVersion();
        return ResponseEntity.ok("Hello World! Spring Boot Version: " + version);
    }
}
