package com.yohoard.categories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yohoard.notes.Note;
import com.yohoard.notes.NoteRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private NoteRepository noteRepository;

  @InjectMocks
  private CategoryService service;

  private final List<String> testNotesIds = new ArrayList<>();

  private final List<Note> testNoteList = new ArrayList<>();
  private final List<Category> testCategoryList = new ArrayList<>();

  private final Category testCategory = new Category("cat1", "important stuff", 10);

  @BeforeEach
  public void init() {
    testCategory.setId("12345");

    testNotesIds.addAll(List.of("333", "4444"));

    testNoteList.addAll(List.of(
        new Note("note1", "some random text about cats", Collections.emptyList(),
            Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
            LocalDateTime.now(), 8),
        new Note("note2", "some random text about dogs", Collections.emptyList(),
            Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(),
            LocalDateTime.now(), 3)
    ));

    testCategoryList.addAll(List.of(
        new Category("cat1", "important stuff", 10),
        new Category("cat2", "for later", 5),
        new Category("cat3", "little things of no urgency", 1)));
  }

  @AfterEach
  void clear() {
    testCategoryList.clear();
  }

  @Test
  void getAllCategories_returnsCategoriesList() {
    when(categoryRepository.findAll()).thenReturn(testCategoryList);

    List<Category> result = service.getAllCategories();

    assertEquals(3, result.size());
    verify(categoryRepository, times(1)).findAll();
  }

  @Test
  void createCategory_returnsCreatedCategory() {
    when(categoryRepository.save(testCategory)).thenReturn(testCategory);

    Category result = service.createCategory(testCategory);

    assertEquals(testCategory, result);
    verify(categoryRepository, times(1)).save(testCategory);
  }

  @Test
  void updateCategory_returnsUpdatedCategoryData() {
    Category originalCategory = testCategory;
    Category editedCategory = new Category("new awesome name", "new and shiny super awesome description", 7);

    when(categoryRepository.findById(originalCategory.getId())).thenReturn(Optional.of(originalCategory));
    when(categoryRepository.save(originalCategory)).thenReturn(editedCategory);

    Category result = service.updateCategory(originalCategory.getId(), editedCategory);

    assertNotNull(result);
    assertEquals(editedCategory, result);
    assertEquals("new awesome name", result.getName());
    assertEquals("new and shiny super awesome description", result.getDescription());

    verify(categoryRepository, times(1)).findById(originalCategory.getId());
    verify(categoryRepository, times(1)).save(originalCategory);
  }

  @Test
  void deleteCategory_deletesCategory_ifCategoryExists() {
    Category category = testCategory;

    when(categoryRepository.findById(testCategory.getId())).thenReturn(Optional.of(testCategory));

    service.deleteCategory(category);

    verify(categoryRepository, times(1)).findById(category.getId());
    verify(categoryRepository, times(1)).delete(category);
  }

  @Test
  void deleteCategory_willNotDeleteCategory_ifCategoryNotExists() {
    Category category = testCategory;

    when(categoryRepository.findById(testCategory.getId())).thenReturn(Optional.empty());

    service.deleteCategory(category);

    verify(categoryRepository, times(1)).findById(testCategory.getId());
    verify(categoryRepository, never()).delete(category);
  }

  @Test
  void addNotes_addsNotesToCategory_ifCategoryExists() {
    List<String> notesIds = testNotesIds;
    Category category = testCategory;
    List<Note> notes = testNoteList;

    when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
    when(noteRepository.findAllById(notesIds)).thenReturn(notes);
    when(categoryRepository.save(category)).thenReturn(category);

    Category result = service.addNotes(notesIds, category.getId());

    verify(categoryRepository, times(1)).findById(category.getId());
    verify(noteRepository, times(1)).findAllById(notesIds);
    verify(categoryRepository, times(1)).save(category);
    assertEquals(notes.size(), result.getNotes().size());
  }

  @Test
  void addNotes_throwsException_ifCategoryNotExists() {
    String categoryId = "this id doesn't exist";
    List<String> notesIds = testNotesIds;

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> service.addNotes(notesIds, categoryId));
  }
}