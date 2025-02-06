package com.app.backend.controller.admin;

import com.app.backend.model.Author;
import com.app.backend.model.Book;
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
@CrossOrigin(origins = "*")
public class AdminBookController {

    @Autowired
    private BookService bookService;

    // Récupérer tous les livres
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Créer un livre
    @PostMapping
    public ResponseEntity<String> createBook(@RequestParam String title,
                                             @RequestParam Long authorId,
                                             @RequestParam MultipartFile file,
                                             @RequestParam MultipartFile image) throws IOException {
        String result = bookService.addBook(title, authorId, file, image);
        if (result.equals("Book created successfully!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
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
