package com.yohoard.notes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("notes")
@Getter
@Setter
public class Note {
    @Id()
    private String id;
    private String name;
    private String text;
    @DBRef
    private List<Category> categories;
    @DBRef
    private List<Tag> tags;
    @DBRef
    private List<Highlight> highlights;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int priority;

    public Note(
            String name,
            String text,
            List<Category> categories,
            List<Tag> tags,
            List<Highlight> highlights,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            int priority
    ) {
        this.name = name;
        this.text = text;
        this.categories = categories;
        this.tags = tags;
        this.highlights = highlights;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.priority = priority;
    }
}
