package com.app.backend.controller.admin;

import com.app.backend.model.Author;
import com.app.backend.model.Book;
import com.app.backend.repository.AuthorRepository;
import com.app.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/books")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")

public class AdminBookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
}

    // Récupérer tous les livres
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Créer un livre
    @PostMapping
    public ResponseEntity<String> createBook(@RequestParam String title,
                                             @RequestParam Long authorId,
                                             @RequestParam(value = "description") String description,
                                             @RequestParam(value = "file") MultipartFile file,
                                             @RequestParam(value = "image",required = false) MultipartFile image) throws IOException {
        String result = bookService.addBook(title, authorId, file, image, description);
        if (result.equals("Book created successfully!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);

        return book.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un livre
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestParam String title,
                                           @RequestParam Long authorId,
                                           @RequestParam MultipartFile file,
                                           @RequestParam MultipartFile image) throws IOException {
        Optional<Book> updatedBook = bookService.updateBook(id, title, authorId, file, image);
        return updatedBook.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Supprimer un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
