package ru.kors.springweb.component;

import org.springframework.stereotype.Component;

@Component
public class HelloWorldComponent {
    public String hello() {
        return "Hello World!";
    }
}
