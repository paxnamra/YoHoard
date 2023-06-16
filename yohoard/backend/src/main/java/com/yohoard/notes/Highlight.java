package com.yohoard.notes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("highlights")
@Getter
@Setter
public class Highlight {
    @Id()
    private String id;
    private String text;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int priority;

    public Highlight(String text, LocalDateTime createdAt, LocalDateTime updatedAt, int priority) {
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.priority = priority;
    }

    public Highlight(String text, int priority) {
        this(text, LocalDateTime.now(), LocalDateTime.now(), priority);
    }
}
