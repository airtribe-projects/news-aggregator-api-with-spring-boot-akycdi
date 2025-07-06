package com.airtribe.news.controller;

import com.airtribe.news.config.JwtUtil;
import com.airtribe.news.dto.LoginRequest;
import com.airtribe.news.dto.RegisterRequest;
import com.airtribe.news.entity.User;
import com.airtribe.news.model.JwtResponse;
import com.airtribe.news.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    //Removed autowired annotation for constructor injection
    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, AuthService authService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }



    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest request) {
       User user = authService.register(request);
       return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
