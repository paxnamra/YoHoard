package com.yohoard.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/dev")
public class DevController {
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final HighlightRepository highlightRepository;

    @Autowired
    public DevController(
            NoteRepository noteRepository,
            CategoryRepository categoryRepository,
            TagRepository tagRepository,
            HighlightRepository highlightRepository
    ) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.highlightRepository = highlightRepository;
    }

    @PostMapping("/fixtures")
    public ResponseEntity<String> addFixtures() {
        Category category = categoryRepository.save(new Category("cat1", "desccat", 5));
        Category category2 = categoryRepository.save(new Category("cat2", "desccat2", 5, category));
        categoryRepository.save(new Category("cat3", "desccat3", 5, category2));

        Tag tag = tagRepository.save(new Tag("tag1", "desctag", 10));
        Tag tag2 = tagRepository.save(new Tag("tag2", "desctag2", 10));

        Highlight highlight = highlightRepository.save(new Highlight("high1", 15));
        Highlight highlight2 = highlightRepository.save(new Highlight("high2", 15));

        noteRepository.save(
                new Note("test",
                        "actual text",
                        List.of(category, category2),
                        List.of(tag, tag2),
                        List.of(highlight, highlight2),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        10
                ));

        return new ResponseEntity<>("Added dev fixtures.", HttpStatus.OK);
    }
}
