package com.app.backend.model;

import jakarta.persistence.*;
import java.util.List;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor // Constructeur sans arguments
@AllArgsConstructor // Constructeur avec tous les arguments
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;


    @OneToMany(mappedBy = "author")
    private List<Book> books;
}
