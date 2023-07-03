package com.yohoard.categories;

import static com.yohoard.TestDataUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yohoard.notes.Note;
import com.yohoard.notes.NoteRepository;
import java.util.ArrayList;
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
  private final List<String> TEST_NOTES_IDS = new ArrayList<>();
  private final List<Note> TEST_NOTES = new ArrayList<>();
  private final List<Category> TEST_CATEGORIES = new ArrayList<>();
  private final Category TEST_CATEGORY = singleTestCategory("12345");

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private NoteRepository noteRepository;

  @InjectMocks
  private CategoryService service;

  @BeforeEach
  public void init() {
    TEST_NOTES_IDS.addAll(notesIds2Items());
    TEST_NOTES.addAll(notes2Items());
    TEST_CATEGORIES.addAll(categories3Items());
  }

  @AfterEach
  void clear() {
    TEST_CATEGORIES.clear();
  }

  @Test
  void getAllCategories_returnsCategoriesList() {
    when(categoryRepository.findAll()).thenReturn(TEST_CATEGORIES);

    List<Category> result = service.getAllCategories();

    assertEquals(3, result.size());
    verify(categoryRepository, times(1)).findAll();
  }

  @Test
  void createCategory_returnsCreatedCategory() {
    when(categoryRepository.save(TEST_CATEGORY)).thenReturn(TEST_CATEGORY);

    Category result = service.createCategory(TEST_CATEGORY);

    assertEquals(TEST_CATEGORY, result);
    verify(categoryRepository, times(1)).save(TEST_CATEGORY);
  }

  @Test
  void updateCategory_returnsUpdatedCategoryData() {
    Category originalCategory = TEST_CATEGORY;
    Category editedCategory = singleTestCategory();

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
    Category category = TEST_CATEGORY;

    when(categoryRepository.findById(TEST_CATEGORY.getId())).thenReturn(Optional.of(TEST_CATEGORY));

    service.deleteCategory(category);

    verify(categoryRepository, times(1)).findById(category.getId());
    verify(categoryRepository, times(1)).delete(category);
  }

  @Test
  void deleteCategory_willNotDeleteCategory_ifCategoryNotExists() {
    Category category = TEST_CATEGORY;

    when(categoryRepository.findById(TEST_CATEGORY.getId())).thenReturn(Optional.empty());

    service.deleteCategory(category);

    verify(categoryRepository, times(1)).findById(TEST_CATEGORY.getId());
    verify(categoryRepository, never()).delete(category);
  }

  @Test
  void addNotes_addsNotesToCategory_ifCategoryExists() {
    List<String> notesIds = TEST_NOTES_IDS;
    Category category = TEST_CATEGORY;
    List<Note> notes = TEST_NOTES;

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
    List<String> notesIds = TEST_NOTES_IDS;

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> service.addNotes(notesIds, categoryId));
  }
}