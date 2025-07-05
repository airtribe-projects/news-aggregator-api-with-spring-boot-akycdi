package com.airtribe.news.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airtribe.news.entity.Preference;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    
}