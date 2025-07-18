package com.expenseModifyTracker.demo.Service;

import com.expenseModifyTracker.demo.Model.Category;

import com.expenseModifyTracker.demo.Model.User;
import com.expenseModifyTracker.demo.Model.UserExpense;
import com.expenseModifyTracker.demo.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class CategoryService {

 private final CategoryRepository categoryRepository;

 @Autowired
 public CategoryService(CategoryRepository categoryRepository) {
     this.categoryRepository = categoryRepository;
 }

 public List<Category> getAllCategory() {
     return this.categoryRepository.findAll();
 }
 public Category getCategoryByID(Long id){
     return this.categoryRepository.findById(id).orElse(null);

    }

 public Category createCategory(Category category){
     return this.categoryRepository.save(category);
 }

    public Category updateCategory(Long id, Category category){
        category.setCategoryID(id);
        Category existingCategory = this.categoryRepository.findById(category.getCategoryID())
                .orElseThrow(() -> new RuntimeException("Category not found"));

       existingCategory.setCategoryName(category.getCategoryName());
       return this.categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isPresent()) {
            this.categoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found.");
        }
    }
}
