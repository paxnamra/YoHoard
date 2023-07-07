package com.yohoard.categories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<Category>> listAll() {
    var allCategories = categoryService.getAllCategories();
    return new ResponseEntity<>(allCategories, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Category> create(@RequestBody Category category) {
    var newCategory = categoryService.createCategory(category);
    return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") String id) {
    categoryService.deleteCategory(id);
    return new ResponseEntity<>("Successfully deleted a category.", HttpStatus.NO_CONTENT);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Category> update(@PathVariable("id") String id, @RequestBody Category category) {
    var updatedCategory = categoryService.updateCategory(id, category);
    return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
  }
}
