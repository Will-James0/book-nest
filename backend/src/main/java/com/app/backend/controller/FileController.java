package com.app.backend.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileController {

    // Répertoires de base pour les différents types de fichiers
    private static final String AUTHOR_IMAGES_DIR = "src/main/resources/static/images/authors/";
    private static final String BOOK_IMAGES_DIR = "src/main/resources/static/images/books/";
    private static final String BOOK_FILES_DIR = "src/main/resources/static/files/books/";

    @GetMapping("/authors/{filename:.+}")
    public ResponseEntity<Resource> getAuthorImage(@PathVariable String filename, HttpServletRequest request) {
        return getFileResponse(AUTHOR_IMAGES_DIR, filename, request);
    }

    @GetMapping("/books/images/{filename:.+}")
    public ResponseEntity<Resource> getBookImage(@PathVariable String filename, HttpServletRequest request) {
        return getFileResponse(BOOK_IMAGES_DIR, filename, request);
    }

    @GetMapping("/books/files/{filename:.+}")
    public ResponseEntity<Resource> getBookFile(@PathVariable String filename, HttpServletRequest request) {
        return getFileResponse(BOOK_FILES_DIR, filename, request);
    }

    private ResponseEntity<Resource> getFileResponse(String directory, String filename, HttpServletRequest request) {
        try {
            Path filePath = Paths.get(directory).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                String contentType = null;
                try {
                    contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                } catch (IOException ex) {
                    // Définir le type de contenu par défaut si la détection échoue
                    contentType = "application/octet-stream";
                }

                // Définir les en-têtes de la réponse
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
