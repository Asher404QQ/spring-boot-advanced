package ru.kors.webflux.controller;

import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/web-flux")
public class WebFluxHelloWorldController {

    @GetMapping
    public Mono<String> hello() {
        String version = SpringBootVersion.getVersion();
        return Mono.just("Hello World! Spring Boot Version: " + version);
    }
}
