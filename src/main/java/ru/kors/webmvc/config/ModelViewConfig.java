package ru.kors.webmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import ru.kors.webmvc.error.CustomizedErrorAttributes;


@Configuration
public class ModelViewConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    @Bean
    public CustomizedErrorAttributes customizedErrorAttributes() {
        return new CustomizedErrorAttributes();
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

//    @Bean
//    public LocaleResolver localResolver() {
//        return new AcceptHeaderLocaleResolver();
//    }

//    @Bean
//    public LocaleResolver localeResolver() {
//        var localeResolver = new SessionLocaleResolver();
//        localeResolver.setDefaultLocale(Locale.forLanguageTag("en"));
//        return localeResolver;
//    }
}
