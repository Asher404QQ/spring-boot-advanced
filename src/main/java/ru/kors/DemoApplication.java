package ru.kors;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import ru.kors.webmvc.model.Book;
import ru.kors.webmvc.service.BookService;

import java.util.Arrays;
import java.util.List;

@PropertySource(value = "classpath:my-customer.properties", ignoreResourceNotFound = true)
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public ApplicationRunner booksInitializer(BookService bookService) {
        return args -> {
            Book book1 = new Book("9378249328729", "Мастер и Маргарита", List.of("Михаил Булгаков"));
            Book book2 = new Book("9378249938272", "Преступление и наказание", List.of("Федор Михайлович Достоевский"));
            Book book3 = new Book("9378748927489", "Маленький принц", List.of("Антуан де Сент-Экзюпери"));

            bookService.save(book1);
            bookService.save(book2);
            bookService.save(book3);

            Arrays.asList(bookService.findAll()).forEach(System.out::println);
        };
    }
}
