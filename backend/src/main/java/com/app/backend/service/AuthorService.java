package com.app.backend.service;

import com.app.backend.model.Author;
import com.app.backend.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/authors/";

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // 🔹 Récupérer tous les auteurs
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // 🔹 Ajouter un auteur
    public String addAuthor(String name, MultipartFile image) throws IOException {
        if (name == null || name.isEmpty()) {
            return "Author name cannot be empty.";
        }

        Author author = new Author();
        author.setName(name);

        if (image != null && !image.isEmpty()) {
            // Vérifier et créer le dossier s'il n'existe pas
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Définir le chemin du fichier
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            // Sauvegarde de l'image sur le disque
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Enregistrer le chemin relatif dans la base de données
            author.setImage("/images/authors/" + fileName);
        }

        authorRepository.save(author);
        return "Author added successfully!";
    }

    // 🔹 Mettre à jour un auteur
    public Optional<Author> updateAuthor(Long id, String name, MultipartFile image) throws IOException {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()) {
            return Optional.empty();
        }

        Author author = authorOptional.get();

        if (name != null && !name.isEmpty()) {
            author.setName(name);
        }

        if (image != null && !image.isEmpty()) {
            // Vérifier et créer le dossier s'il n'existe pas
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Définir le nouveau chemin du fichier
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            // Supprimer l'ancienne image si elle existe
            if (author.getImage() != null) {
                Path oldImagePath = Paths.get("src/main/resources/static", author.getImage());
                Files.deleteIfExists(oldImagePath);
            }

            // Sauvegarde de la nouvelle image
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            author.setImage("/images/authors/" + fileName);
        }

        return Optional.of(authorRepository.save(author));
    }

    // 🔹 Supprimer un auteur
    public boolean deleteAuthor(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();

            // Supprimer l'image du dossier si elle existe
            if (author.getImage() != null) {
                Path imagePath = Paths.get("src/main/resources/static", author.getImage());
                try {
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    e.printStackTrace(); // Log de l'erreur
                }
            }

            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
