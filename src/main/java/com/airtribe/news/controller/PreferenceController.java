package com.airtribe.news.controller;

import com.airtribe.news.dto.PreferenceRequest;
import com.airtribe.news.entity.Preference;
import com.airtribe.news.service.PreferenceService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @GetMapping
    public ResponseEntity<Preference> getPreferences(Authentication authentication) {
        String username = authentication.getName();
        Preference pref = preferenceService.getPreferences(username);
        return ResponseEntity.ok(pref);
    }

    @PutMapping
    public ResponseEntity<Preference> updatePreferences(@Valid @RequestBody PreferenceRequest request, Authentication authentication) {
        String username = authentication.getName();
        Preference updated = preferenceService.updatePreferences(username, request);
        return ResponseEntity.ok(updated);
    }
}
