package com.airtribe.news.controller;

import com.airtribe.news.entity.User;
import com.airtribe.news.repo.UserRepository;
import com.airtribe.news.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  NewsService newsService;

    @GetMapping("")
    public ResponseEntity<String> getNews(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        if (user.getPreference() == null) {
            return ResponseEntity.badRequest().body("No preferences set for user");
        }
        String news = newsService.fetchNews(user.getPreference());
        return ResponseEntity.ok(news);
    }
}
