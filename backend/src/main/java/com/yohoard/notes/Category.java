package com.yohoard.notes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id()
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int priority;
    private String path;

    public Category(String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt, int priority, Category parent) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.priority = priority;
        this.path = parent != null ? parent.getPath() + parent.getId() + "," : ",";
    }

    public Category(String name, String description, int priority) {
        this(name, description, LocalDateTime.now(), LocalDateTime.now(), priority, null);
    }

    public Category(String name, String description, int priority, Category parent) {
        this(name, description, LocalDateTime.now(), LocalDateTime.now(), priority, parent);
        this.path = parent.getPath() + parent.getId() + ",";
    }
}
