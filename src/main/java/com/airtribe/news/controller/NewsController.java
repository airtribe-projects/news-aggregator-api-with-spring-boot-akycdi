package com.airtribe.news.controller;

import com.airtribe.news.entity.Preference;
import com.airtribe.news.entity.User;
import com.airtribe.news.repo.UserRepository;
import com.airtribe.news.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  NewsService newsService;

    @GetMapping
    public ResponseEntity<?> getPersonalizedNews(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Preference preference = user.getPreference();
        if (preference == null) {
            return ResponseEntity.badRequest().body("No preferences set for user.");
        }

        String response = newsService.fetchNews(preference);
        return ResponseEntity.ok(response);
    }

   
    @GetMapping("/top-headlines")
    public ResponseEntity<?> getTopHeadlines(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String country) {

        String response = newsService.getTopHeadlines(Optional.ofNullable(category), Optional.ofNullable(country));
        return ResponseEntity.ok(response);
    }

  
    @GetMapping("/search")
    public ResponseEntity<?> searchNews(@RequestParam("q") String query) {
        String response = newsService.searchNews(query);
        return ResponseEntity.ok(response);
    }

   
    @GetMapping("/sources")
    public ResponseEntity<?> getNewsSources() {
        String response = newsService.getNewsSources();
        return ResponseEntity.ok(response);
    }
}
