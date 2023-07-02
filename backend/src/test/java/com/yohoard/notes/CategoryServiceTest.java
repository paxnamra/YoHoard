package com.yohoard.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
  private CategoryRepository repository;

  @InjectMocks
  private CategoryService service;

  private final List<Category> testCategoryList = new ArrayList<>();

  private final Category testCategory = new Category("cat1", "important stuff", 10);

  @BeforeEach
  public void init() {
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
    when(repository.findAll()).thenReturn(testCategoryList);

    List<Category> result = service.getAllCategories();

    assertEquals(3, result.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void createCategory_returnsCreatedCategory() {
    when(repository.save(testCategory)).thenReturn(testCategory);

    Category result = service.createCategory(testCategory);

    assertEquals(testCategory, result);
    verify(repository, times(1)).save(testCategory);
  }
}