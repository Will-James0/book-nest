package com.app.backend.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.backend.model.Author;
import com.app.backend.service.AuthorService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/authors")
public class AdminAuthorController {

    private final AuthorService authorService;

    public AdminAuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //  Récupérer tous les auteurs
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    //  Ajouter un auteur
    @PostMapping("/save")
    public ResponseEntity<String> addAuthor(@RequestParam("name") String name,
                                            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            String result = authorService.addAuthor(name, image);
            if (result.equals("Author added successfully!")) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to save author: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);

        return author.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //  Mettre à jour un auteur
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable long id,
                                               @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Optional<Author> updatedAuthor = authorService.updateAuthor(id, name, image);
            return updatedAuthor.map(ResponseEntity::ok)
                                .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //  Supprimer un auteur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        boolean deleted = authorService.deleteAuthor(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
