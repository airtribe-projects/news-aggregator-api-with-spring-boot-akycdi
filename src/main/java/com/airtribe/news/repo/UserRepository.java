package com.airtribe.news.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airtribe.news.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

