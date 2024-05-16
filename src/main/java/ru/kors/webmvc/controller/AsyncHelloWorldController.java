package ru.kors.webmvc.controller;

import org.springframework.boot.SpringBootVersion;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("/api/v1/async")
public class AsyncHelloWorldController {
    private final AsyncTaskExecutor asyncTaskExecutor;

    public AsyncHelloWorldController(AsyncTaskExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @GetMapping("/hello")
    public CompletableFuture<String> hello() {
        return asyncTaskExecutor.submitCompletable(() -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
                String version = SpringBootVersion.getVersion();
                return String.format("Async Hello World! Spring Boot Version: %s!", version);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

/*    @GetMapping("/hello")
    public Callable<String> hello() {
        return () -> {
            Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
            String version = SpringBootVersion.getVersion();
            return String.format("Async Hello World! Spring Boot Version: %s!", version);
        };
    }*/
}
