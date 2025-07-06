package com.airtribe.news.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airtribe.news.entity.Preference;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    
}