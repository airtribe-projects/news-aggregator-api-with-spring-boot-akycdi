package com.airtribe.news.service;

import com.airtribe.news.entity.Preference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private  WebClient.Builder webClientBuilder;

    @Value("${newsapi.apiKey}")
    private String apiKey;

    private final String BASE_URL = "https://newsapi.org/v2";

    public String fetchNews(Preference preference) {
        return webClientBuilder.baseUrl(BASE_URL).build()
                .get()
                .uri(uriBuilder -> buildTopHeadlinesUri(uriBuilder,
                        Optional.ofNullable(preference.getCategory()),
                        Optional.ofNullable(preference.getCountry()),
                        Optional.ofNullable(preference.getLanguage())))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getTopHeadlines(Optional<String> category, Optional<String> country) {
        return webClientBuilder.baseUrl(BASE_URL).build()
                .get()
                .uri(uriBuilder -> buildTopHeadlinesUri(uriBuilder, category, country, Optional.empty()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String searchNews(String query) {
        return webClientBuilder.baseUrl(BASE_URL).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/everything")
                        .queryParam("q", query)
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getNewsSources() {
        return webClientBuilder.baseUrl(BASE_URL).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/top-headlines/sources")
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private java.net.URI buildTopHeadlinesUri(UriBuilder uriBuilder,
                                              Optional<String> category,
                                              Optional<String> country,
                                              Optional<String> language) {
        UriBuilder builder = uriBuilder.path("/top-headlines")
                .queryParam("apiKey", apiKey);
        category.ifPresent(c -> builder.queryParam("category", c));
        country.ifPresent(c -> builder.queryParam("country", c));
        language.ifPresent(l -> builder.queryParam("language", l));
        return builder.build();
    }
}
