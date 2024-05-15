package ru.kors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import ru.kors.webmvc.error.CustomizedErrorAttributes;

@PropertySource(value = "classpath:my-customer.properties", ignoreResourceNotFound = true)
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CustomizedErrorAttributes customizedErrorAttributes() {
        return new CustomizedErrorAttributes();
    }
}
