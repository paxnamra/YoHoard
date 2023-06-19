package com.yohoard.notes.importer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.yohoard.notes.importer.chrome.ChromeImporter;
import com.yohoard.notes.importer.firefox.FirefoxImporter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class BookmarksImporterController {
    private final FirefoxImporter firefoxImporter;
    private final ChromeImporter chromeImporter;

    public BookmarksImporterController(FirefoxImporter firefoxImporter, ChromeImporter chromeImporter) {
        this.firefoxImporter = firefoxImporter;
        this.chromeImporter = chromeImporter;
    }

    @PostMapping("/import")
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File to be imported is empty.");
        }

        Importer importer = switch (Objects.requireNonNull(file.getContentType())) {
            case "application/json" -> firefoxImporter;
            case "text/html" -> chromeImporter;
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only bookmarks exported from Firefox and Chrome are supported."
            );
        };

        try (InputStream inputStream = file.getInputStream()) {
            importer.run(inputStream);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to import bookmarks from file.");
        }

        return ResponseEntity.noContent().build();
    }
}