package ru.kors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import ru.kors.calculator.Calculator;

@PropertySource(value = "classpath:my-customer.properties", ignoreResourceNotFound = true)
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public ApplicationRunner calculationRunner(Calculator calculator,
                                               @Value("${calculator.lhs:322}") int lhs,
                                               @Value("${calculator.rhs:228}") int rhs,
                                               @Value("${calculator.op:+}") String op) {
        return args -> {
            calculator.calculate(lhs, rhs, op);
        };
    }
}
