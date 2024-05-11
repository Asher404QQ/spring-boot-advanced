package ru.kors.component;

import org.springframework.stereotype.Component;

@Component
public class HelloWorldComponent {
    public String hello() {
        return "Hello World!";
    }
}
