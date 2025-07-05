package com.airtribe.news.controller;

import com.airtribe.news.dto.RegisterRequest;
import com.airtribe.news.dto.LoginRequest;
import com.airtribe.news.entity.User;
import com.airtribe.news.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.authenticate(request);
        return ResponseEntity.ok(user);
    }
}
