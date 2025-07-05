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
        String apiKey = "d413baca255341cf95e18dc6a7b4de1b";
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("newsapi.org")
                        .path("/v2/top-headlines")
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
