# News-API---Spring-API

##Introduction
This is a News API service built using Spring Boot. It allows users to fetch top news articles, search them by keyword, author or title. It also handles caching.

## Features

- Fetch top articles
- Search news articles by keyword
- Find news articles by title
- Find news articles by author
- Caching to reduce redundancy
- Swagger for API documentation
- Basic authentication using Spring Security

## Technologies Used

- Spring Boot
- Spring Security
- Spring Cache
- RestTemplate
- Swagger (Springdoc OpenAPI)
- Maven

##Setup Instructions

####Prerequisites
--Java 17 or later
--Maven
--Git

####Installation

--Clone the repository---
```bash
   git clone https://github.com/yashaswini27/News-API---Spring-API.git

####Build the project
mvn clean install

####Run the application
mvn spring-boot:run

##Security
This project uses basic authentication.
username: newsapi
password: springapi

##API Endpoints

Fetch Top Articles
URL: /news
Method: GET
Parameters:
count (optional): Number of headlines to retrieve (default: 10)
Response: List of top headline articles

Search News Articles by Keyword
URL: /search
Method: GET
Parameters:
keyword: The keyword to search for articles
Response: List of articles matching the keyword

Search News Articles by Title
URL: /searchByTitle
Method: GET
Parameters:
title: The title of the article to search for
Response: List of articles with the specified title

Search News Articles by Author
URL: /searchByAuthor
Method: GET
Parameters:
author: The author of the articles to search for
Response: List of articles written by the specified author

##Swagger Documentation
http://localhost:8080/swagger-ui.html

##Contact
For any questions or inquiries, please contact:
Name: Yashaswini Golla
email: yashaswini99g@gmail.com
