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

  //public void - add notes to category (List<Notes> notes)

  public void deleteCategory(Category category) {
    // some logic here
    // for category with notes and without
  }

  //private boolean - areNotesInCategory(Category category)

}
