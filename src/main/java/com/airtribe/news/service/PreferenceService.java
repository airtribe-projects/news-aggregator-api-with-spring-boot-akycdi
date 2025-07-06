package com.airtribe.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.airtribe.news.dto.PreferenceRequest;
import com.airtribe.news.entity.Preference;
import com.airtribe.news.entity.User;
import com.airtribe.news.repo.PreferenceRepository;
import com.airtribe.news.repo.UserRepository;

@Service
public class PreferenceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;

    public Preference getPreferences(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user.getPreference();
    }

    public Preference updatePreferences(String username, PreferenceRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Preference preference = user.getPreference();
        if (preference == null) {
            preference = new Preference();
        }

        preference.setCategory(request.getCategory());
        preference.setLanguage(request.getLanguage());
        preference.setCountry(request.getCountry());

        preferenceRepository.save(preference);
        user.setPreference(preference);
        userRepository.save(user);

        return preference;
    }
}

