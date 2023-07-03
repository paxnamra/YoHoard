package com.yohoard;

import com.yohoard.categories.Category;
import com.yohoard.notes.Note;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TestDataUtil {

  public static Category singleTestCategory() {
    Category category = new Category("new awesome name",
        "new and shiny super awesome description", 7);
    category.setId("54321");
    return category;
  }

  public static Category singleTestCategory(String categoryId) {
    Category category = new Category("cat1", "important stuff", 10);
    category.setId(categoryId);
    return category;
  }

  public static List<String> notesIds2Items() {
    return List.of("333", "4444");
  }

  public static List<Note> notes2Items() {
    return List.of(
        new Note("note1", "some random text about cats", Collections.emptyList(),
            Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
            LocalDateTime.now(), 8),
        new Note("note2", "some random text about dogs", Collections.emptyList(),
            Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
            LocalDateTime.now(), 3)
    );
  }

  public static List<Category> categories3Items() {
    return List.of(
        new Category("cat1", "important stuff", 10),
        new Category("cat2", "for later", 5),
        new Category("cat3", "little things of no urgency", 1));
  }
}
