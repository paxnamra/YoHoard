package com.yohoard;

import com.yohoard.categories.Category;
import com.yohoard.notes.Note;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    category.setNotes(new ArrayList<>());
    return category;
  }

  public static Category singleTestCategoryWithNotes(String categoryId) {
    Category category = new Category("cat1", "important stuff", 10);
    category.setId(categoryId);
    category.setNotes(notesWith5Items());
    return category;
  }

  public static List<String> notesIds2Items() {
    return List.of("333", "4444");
  }

  public static List<Note> notesWith5Items() {
    Note note1 = new Note("note1", "some random text about cats", Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
        LocalDateTime.now(), 8);

    Note note2 = new Note("note2", "some random text about dogs", Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
        LocalDateTime.now(), 3);

    Note note3 = new Note("note3", "additional random", Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
        LocalDateTime.now(), 3);

    Note note4 = new Note("note4", "NOPE", Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
        LocalDateTime.now(), 3);

    Note note5 = new Note("note5", "in the end", Collections.emptyList(),
        Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
        LocalDateTime.now(), 3);

    note1.setId("333");
    note2.setId("4444");
    note3.setId("1");
    note4.setId("22");
    note5.setId("8989");

    return new ArrayList<>(Arrays.asList(note1, note2, note3, note4, note5));
  }

  public static List<Category> categories3Items() {
    return List.of(
        new Category("cat1", "important stuff", 10),
        new Category("cat2", "for later", 5),
        new Category("cat3", "little things of no urgency", 1));
  }
}
