package com.app.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor // Constructeur sans arguments
@AllArgsConstructor //Constructeur avec arguments
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column (columnDefinition = "TEXT")
    private String description;

    private String file;

    private String image;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
