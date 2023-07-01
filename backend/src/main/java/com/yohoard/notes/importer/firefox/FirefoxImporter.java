package com.yohoard.notes.importer.firefox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yohoard.notes.*;
import com.yohoard.notes.importer.Importer;
import com.yohoard.util.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FirefoxImporter implements Importer {
    private static final int BOOKMARK_TYPE_CODE = 1;
    private static final int FOLDER_TYPE_CODE = 2;

    private final NoteService noteService;
    private final CategoryRepository categoryRepository;
    private final TagService tagService;

    public FirefoxImporter(
            NoteService noteService,
            TagService tagService,
            CategoryRepository categoryRepository
    ) {
        this.noteService = noteService;
        this.tagService = tagService;
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
            List<Tag> tags = item.tags() != null
                    ? tagService.getOrCreate(Set.of(item.tags().split(",")))
                    : new ArrayList<>();

            noteService.createNote(
                    new Note(
                            item.title(),
                            item.uri(),
                            parentCategory == null ? new ArrayList<>() : List.of(parentCategory),
                            tags,
                            new ArrayList<>(),
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            0
                    )
            );

            return;
        }

        Category category = null;

        // only automatically generated folders have 'root' set
        if (item.typeCode() == FOLDER_TYPE_CODE && item.root() == null) {
            category = categoryRepository.save(
                    new Category(
                            item.title(),
                            "",
                            DateTimeUtils.fromMicros(item.dateAdded()),
                            DateTimeUtils.fromMicros(item.lastModified()),
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
