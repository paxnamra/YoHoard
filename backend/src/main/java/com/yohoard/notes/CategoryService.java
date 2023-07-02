package com.yohoard.notes;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private NoteRepository noteRepository;

  public List<Category> getAllCategories() {
    var categories = categoryRepository.findAll();
    LOG.info("Sent categoriesList of size: {}", categories.size());
    return categories;
  }

  public Category createCategory(Category category) {
    var newCategory = categoryRepository.save(category);
    LOG.info("Successfully created a category.");
    return newCategory;
  }

  public Category updateCategory(String categoryId, Category editedCategory) {
    var originalCategory = categoryRepository.findById(categoryId);

    if (originalCategory.isPresent()) {
      Category category = originalCategory.get();
      category.setName(editedCategory.getName());
      category.setDescription(editedCategory.getDescription());
      category.setUpdatedAt(editedCategory.getUpdatedAt());
      category.setPriority(editedCategory.getPriority());

      categoryRepository.save(category);
      LOG.info("Successfully updated category with id: {}", categoryId);
    } else {
      LOG.warn("Can't update category of given id: {}", categoryId);
    }

    return editedCategory;
  }

  public void deleteCategory(Category category) {
    var categoryToDelete = categoryRepository.findById(category.getId());

    if (categoryToDelete.isPresent()) {
      categoryToDelete.ifPresent(ctd -> categoryRepository.delete(ctd));
      LOG.info("Successfully deleted category of id: {} and name: {}.",
          categoryToDelete.get().getId(),
          categoryToDelete.get().getName());
    }
  }

  public Category addNotes(List<String> notesIds, String categoryId) {
    var category = categoryRepository.findById(categoryId);
    var notes = noteRepository.findAllById(notesIds);

    if (category.isPresent()) {
      category.get().getNotes().addAll(notes);
      categoryRepository.save(category.get());
      LOG.info("Added notes to category of id: {} and name: {}. ",
          category.get().getId(),
          category.get().getName());
    }

    return category.orElseThrow();
  }

  public void removeNotes(List<String> notesIds, String categoryId) {
    var category = categoryRepository.findById(categoryId);
    var notes = noteRepository.findAllById(notesIds);

    if (category.isPresent()) {
      category.get().getNotes().removeAll(notes);
      categoryRepository.save(category.get());
      LOG.info("Removed {} notes from category id: {} and name: {}. ",
          notesIds.size(),
          category.get().getId(),
          category.get().getName());
    }
  }

  public Category moveNotes(String currentCategoryId, String anotherCategoryId) {
    var current = categoryRepository.findById(currentCategoryId);
    var another = categoryRepository.findById(anotherCategoryId);

    if (current.isPresent() && another.isPresent()) {
      var notes = current.get().getNotes();
      another.get().getNotes().addAll(notes);
      current.get().getNotes().clear();
      LOG.info("Moved {} notes from category id: {} and name: {} to category id: {} and name: {}. ",
          notes.size(),
          currentCategoryId,
          current.get().getName(),
          anotherCategoryId,
          another.get().getName());
    } else {
      LOG.warn("Can't move notes from category id: {} to category id: {}. ",
          currentCategoryId,
          anotherCategoryId);
    }

    return another.orElseThrow();
  }
}
