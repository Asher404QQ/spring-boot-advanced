package ru.kors.springweb.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SayHelloWorld {

    @Autowired
    public SayHelloWorld(HelloWorldComponent helloWorldComponent) {
        String hello = helloWorldComponent.hello();
        System.out.println(hello + " From SayHelloWorld.class");
    }
}
