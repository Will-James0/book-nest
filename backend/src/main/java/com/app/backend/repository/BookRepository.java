package com.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}

