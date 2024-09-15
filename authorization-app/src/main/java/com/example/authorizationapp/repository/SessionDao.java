package com.example.authorizationapp.repository;

import com.example.authorizationapp.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionDao extends JpaRepository<Session, Integer> {
    Session findByToken(String token);
}
