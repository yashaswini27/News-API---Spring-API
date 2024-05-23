package com.example.newsapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.newsapi.model.Article;
import com.example.newsapi.model.NewsApiResponse;

@Service
public class NewsService {

    @Value("${newsapi.api.url}")
    private String apiUrl;

    @Value("${newsapi.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "topArticles", key = "#count")
    public List<Article> getTopArticles(int count) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("apiKey", apiKey)
                .queryParam("pageSize", count)
                .queryParam("country", "us")
                .toUriString();
        logger.info("Fetching top articles from URL: {}", url);
        NewsApiResponse response = restTemplate.getForObject(url, NewsApiResponse.class);
        return response != null ? response.getArticles() : null;
    }
    
    @Cacheable(value = "articles", key = "#keyword")
    public List<Article> searchArticlesByKeyword(String keyword) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("apiKey", apiKey)
                .queryParam("q", keyword)
                .toUriString();
        logger.info("Searching articles with keyword from URL: {}", url);
        NewsApiResponse response = restTemplate.getForObject(url, NewsApiResponse.class);
        return response != null ? response.getArticles() : null;
    }

    @Cacheable(value = "articlesByTitle", key = "#title")
    public List<Article> findArticlesByTitle(String title) {
        // Fetch more articles
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("apiKey", apiKey)
                .queryParam("articleCount", 100) 
                .queryParam("language", "en")
                .toUriString();
        logger.info("Fetching more articles from the URL: {}", url);
        NewsApiResponse response = restTemplate.getForObject(url, NewsApiResponse.class);

        if (response == null || response.getArticles() == null) {
            logger.error("No articles found with this title even after fetching many articles");
            return null;
        }

        logger.info("Fetched {} articles", response.getArticles().size());
        List<Article> filteredArticles = response.getArticles().stream()
                .peek(article -> logger.info("check article: {}", article.getTitle()))
                .filter(article -> title.equalsIgnoreCase(article.getTitle()))
                .collect(Collectors.toList());

        logger.info("Filtered to {} articles by title: {}", filteredArticles.size(), title);
        return filteredArticles;
    }

    @Cacheable(value = "articlesByAuthor", key = "#author")
    public List<Article> findArticlesByAuthor(String author) {
        // Fetch more articles
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("apiKey", apiKey)
                .queryParam("articleCount", 100) // Fetch a larger number of articles
                .queryParam("language", "en")
                .toUriString();
        logger.info("Fetching more articles from URL: {}", url);
        NewsApiResponse response = restTemplate.getForObject(url, NewsApiResponse.class);

        if (response == null || response.getArticles() == null) {
            logger.error("No articles found for this author even after fetching large number of articles");
            return null;
        }

        logger.info("Fetched {} articles", response.getArticles().size());
        List<Article> filteredArticles = response.getArticles().stream()
                .filter(article -> author.equalsIgnoreCase(article.getAuthor()))
                .collect(Collectors.toList());

        logger.info("Filtered to {} articles by author: {}", filteredArticles.size(), author);
        return filteredArticles;
    }

}
