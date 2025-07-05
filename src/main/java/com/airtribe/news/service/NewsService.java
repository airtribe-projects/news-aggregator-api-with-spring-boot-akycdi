package com.airtribe.news.service;

import com.airtribe.news.entity.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final WebClient.Builder webClientBuilder;

    public String fetchNews(Preference preference) {
        // Example: Use NewsAPI.org (replace with your API key and endpoint)
        String url = "https://newsapi.org/v2/top-headlines";
        String apiKey = "d413baca255341cf95e18dc6a7b4de1b";
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("category", preference.getCategory())
                        .queryParam("language", preference.getLanguage())
                        .queryParam("country", preference.getCountry())
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
