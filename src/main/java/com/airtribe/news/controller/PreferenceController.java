package com.airtribe.news.controller;

import com.airtribe.news.dto.PreferenceRequest;
import com.airtribe.news.entity.Preference;
import com.airtribe.news.entity.User;
import com.airtribe.news.repo.PreferenceRepository;
import com.airtribe.news.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class PreferenceController {
    private final UserRepository userRepository;
    private final PreferenceRepository preferenceRepository;

    @GetMapping("")
    public ResponseEntity<Preference> getPreferences(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(user.getPreference());
    }

    @PutMapping("")
    public ResponseEntity<Preference> updatePreferences(@Valid @RequestBody PreferenceRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Preference pref = user.getPreference();
        if (pref == null) pref = new Preference();
        pref.setCategory(request.getCategory());
        pref.setLanguage(request.getLanguage());
        pref.setCountry(request.getCountry());
        preferenceRepository.save(pref);
        user.setPreference(pref);
        userRepository.save(user);
        return ResponseEntity.ok(pref);
    }
}
