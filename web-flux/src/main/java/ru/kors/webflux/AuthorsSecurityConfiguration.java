package ru.kors.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class AuthorsSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .formLogin(Customizer.withDefaults())
                .headers(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .csrf(Customizer.withDefaults()).build();
    }
}
