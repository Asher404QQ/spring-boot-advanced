package ru.kors.webmvc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import ru.kors.webmvc.client.AuthorClient;
import ru.kors.webmvc.client.PostClient;
import ru.kors.webmvc.error.CustomizedErrorAttributes;


@Configuration
public class ModelViewConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(RestClient.Builder builder) {
        var adapter = RestClientAdapter.create(builder.build());
        return HttpServiceProxyFactory.builderFor(adapter).build();
    }

    @Bean
    public AuthorClient authorClient(HttpServiceProxyFactory factory) {
        return factory.createClient(AuthorClient.class);
    }

    @Bean
    public PostClient postClient(HttpServiceProxyFactory factory) {
        return factory.createClient(PostClient.class);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(5000);
        configurer.setTaskExecutor(threadPoolTaskExecutor());
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        var threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("mvc-executor-");
        return threadPoolTaskExecutor;
    }

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
