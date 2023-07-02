package com.yohoard;

import com.yohoard.categories.Category;
import com.yohoard.categories.CategoryRepository;
import com.yohoard.notes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class YohoardApplication implements ApplicationRunner {

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final HighlightRepository highlightRepository;

    @Autowired
    public YohoardApplication(
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

    public static void main(String[] args) {
        SpringApplication.run(YohoardApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
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
    }
}
