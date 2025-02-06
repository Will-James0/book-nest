package com.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

