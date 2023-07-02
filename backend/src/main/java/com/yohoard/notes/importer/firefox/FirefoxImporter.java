package com.yohoard.notes.importer.firefox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yohoard.categories.Category;
import com.yohoard.categories.CategoryRepository;
import com.yohoard.notes.Note;
import com.yohoard.notes.NoteRepository;
import com.yohoard.notes.importer.Importer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class FirefoxImporter implements Importer {
    private static final int BOOKMARK_TYPE_CODE = 1;
    private static final int FOLDER_TYPE_CODE = 2;

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    public FirefoxImporter(NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(InputStream inputStream) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        BookmarkItem rootBookmarkItem = mapper.readValue(inputStream, BookmarkItem.class);

        importItems(rootBookmarkItem, null);
    }

    private void importItems(BookmarkItem item, Category parentCategory) {
        if (item.typeCode() == BOOKMARK_TYPE_CODE) {
            noteRepository.save(
                    new Note(
                            item.title(),
                            item.uri(),
                            parentCategory == null ? new ArrayList<>() : List.of(parentCategory),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            0
                    ));

            return;
        }

        Category category = null;

        // only automatically generated folders have 'root' set
        if (item.root() == null) {
            category = categoryRepository.save(
                     new Category(
                            item.title(),
                            "",
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(item.dateAdded()), ZoneId.of("Europe/Warsaw")),
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(item.lastModified()), ZoneId.of("Europe/Warsaw")),
                            0,
                             parentCategory
                    )
            );
        }

        if (item.children() != null) {
            for (BookmarkItem child : item.children()) {
                importItems(child, category);
            }
        }
    }
}
