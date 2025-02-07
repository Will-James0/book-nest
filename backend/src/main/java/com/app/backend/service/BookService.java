package com.app.backend.service;

import com.app.backend.model.Book;
import com.app.backend.model.Author;
import com.app.backend.repository.BookRepository;
import com.app.backend.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private static final String UPLOAD_IMAGE_DIR = "src/main/resources/static/images/books/";
    private static final String UPLOAD_FILE_DIR = "src/main/resources/static/files/books/";

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // Récupérer tous les livres
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Créer un livre
    public String addBook(String title, Long authorId, MultipartFile file, MultipartFile image, String description) throws IOException {
        if (title == null || title.isEmpty()) {
            return "Book title cannot be empty.";
        }

        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            return "Author not found.";
        }

        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setAuthor(optionalAuthor.get());

        if (file != null && !file.isEmpty()) {
            // Vérifier et créer le dossier s'il n'existe pas
            File uploadDir = new File(UPLOAD_FILE_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_FILE_DIR, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            book.setFile(filePath.toString());
        }

        if (image != null && !image.isEmpty()) {
            // Vérifier et créer le dossier s'il n'existe pas
            File uploadDir = new File(UPLOAD_IMAGE_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(UPLOAD_IMAGE_DIR, imageName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            book.setImage(imagePath.toString());
        }

        bookRepository.save(book);
        return "Book created successfully!";
    }

    // Mettre à jour un livre
    public Optional<Book> updateBook(Long id, String title, Long authorId, MultipartFile file, MultipartFile image) throws IOException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return Optional.empty();
        }

        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            return Optional.empty();
        }

        Book book = optionalBook.get();
        Author author = optionalAuthor.get();

        book.setTitle(title);
        book.setAuthor(author);

        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_FILE_DIR, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            book.setFile(filePath.toString());
        }

        if (image != null && !image.isEmpty()) {
            String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(UPLOAD_IMAGE_DIR, imageName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            book.setImage(imagePath.toString());
        }

        return Optional.of(bookRepository.save(book));
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Supprimer un livre
    public boolean deleteBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
