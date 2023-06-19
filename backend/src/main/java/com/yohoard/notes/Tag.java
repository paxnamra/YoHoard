package com.yohoard.notes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("tags")
@Getter
@Setter
@NoArgsConstructor
public class Tag {
    @Id()
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int priority;

    public Tag(String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt, int priority) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.priority = priority;
    }

    public Tag(String name, String description, int priority) {
        this(name, description, LocalDateTime.now(), LocalDateTime.now(), priority);
    }
}
