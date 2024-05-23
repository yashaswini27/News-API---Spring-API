package com.example.newsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
public class NewsApiSpringApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsApiSpringApiApplication.class, args);
    }

    //Used RestTemplate to interact with external News API
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
