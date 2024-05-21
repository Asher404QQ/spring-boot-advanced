package ru.kors.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class LibrarySecurityConfig implements WebMvcConfigurer {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers(Customizer.withDefaults())
                .anonymous(anon -> anon.principal("guest").authorities("ROLE_GUEST"))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(
                        login -> login.loginPage("/login").permitAll()
                                .defaultSuccessUrl("/api/v1/security/books/library.html", true)
                                .failureUrl("/login?error=true")
                )
                .logout(logout -> logout.logoutSuccessUrl("/logout"))
                .rememberMe(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var adminUser = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities("ADMIN", "USER").build();

        var normalUser = User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .authorities("USER").build();

        var disableUser = User.withUsername("disabled")
                .password(passwordEncoder.encode("unknown"))
                .disabled(true)
                .authorities("USER").build();

        return new InMemoryUserDetailsManager(adminUser, normalUser, disableUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
