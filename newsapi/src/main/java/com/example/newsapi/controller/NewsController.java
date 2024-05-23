package com.example.newsapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.newsapi.model.Article;
import com.example.newsapi.service.NewsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class NewsController {

    private final NewsService newsService;
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    
    //Endpoint to fetch top articles
    @Operation(summary = "Fetch news articles", description = "Retrieve top news articles with count parameter (optional).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the article"),
        @ApiResponse(responseCode = "400", description = "Invalid count supplied"),
        @ApiResponse(responseCode = "404", description = "article not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/news")
    public List<Article> getNews(@Parameter(description = "Number of articles to retrieve", example = "10")
                                 @RequestParam(value = "count", defaultValue = "10") int count) {
        return newsService.getTopArticles(count);
    }

    //Endpoint to search news articles by keyword
    @Operation(summary = "Search news articles", description = "Search news articles by a keyword.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the articles"),
        @ApiResponse(responseCode = "400", description = "Invalid keyword"),
        @ApiResponse(responseCode = "404", description = "Articles not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public List<Article> searchNews(@Parameter(description = "Keyword to search articles", example = "technology")
                                    @RequestParam(value = "keyword") String keyword) {
        return newsService.searchArticlesByKeyword(keyword);
    }

    //Endpoint to find articles by title
    @Operation(summary = "Find articles by title", description = "Find news articles by a specific title.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the articles"),
        @ApiResponse(responseCode = "400", description = "Invalid title supplied"),
        @ApiResponse(responseCode = "404", description = "Articles not found with this title"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/findByTitle")
    public ResponseEntity<List<Article>> FindByTitle(@Parameter(description = "Title of the article", example = "Federal judge found ‘strong evidence’ of crimes before Trump was charged in classified documents case - CNN")
                                                       @RequestParam(value = "title") String title) {
        logger.info("Request to find articles with a specific title: {}", title);
        List<Article> articles = newsService.findArticlesByTitle(title);
        if (articles.isEmpty() || articles == null) {
            logger.info("No articles found with this title: {}", title);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(articles);
    }

    //Endpoint to find articles by author/authors (if there are multiple authors for the same article)
    @Operation(summary = "Find articles by author name/names", description = "Search for news articles by a specific author or authors.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the articles"),
        @ApiResponse(responseCode = "400", description = "Invalid author"),
        @ApiResponse(responseCode = "404", description = "Articles not found for this author"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/findByAuthor")
    public ResponseEntity<List<Article>> findByAuthor(@Parameter(description = "Author of the article", example = "Tom Warren")
                                                        @RequestParam(value = "author") String author) {
        logger.info("Request to find articles with a specific author: {}", author);
        List<Article> articles = newsService.findArticlesByAuthor(author);
        if (articles.isEmpty() || articles == null) {
            logger.info("No articles found for this author: {}", author);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(articles);
    }


    
    
   
}

