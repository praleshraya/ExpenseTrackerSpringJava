package com.expenseModifyTracker.demo.Controller;

import com.expenseModifyTracker.demo.Model.Category;
import com.expenseModifyTracker.demo.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = this.categoryService.getAllCategory();
        return ResponseEntity.ok(categories);

    }
    @GetMapping("/{categoryID}")
    public ResponseEntity<?> getCategoryByID(@PathVariable Long categoryID) {
        Category category = this.categoryService.getCategoryByID(categoryID);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cannot find category");
        }
        return ResponseEntity.ok(category);
    }
        //url: localhost:8080/categories

        @PostMapping
        public ResponseEntity<String> createCategory(@RequestBody @Valid Category categoryRequest){
        System.err.println("inside create category function - Spring Boot Backend.");
        Category category = this.categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok("Category created successfully.");
    }

//        @PostMapping("/categories")
//        public ResponseEntity<Map<String, String>> createCategory(@RequestBody Category category) {
//            categoryService.createCategory(category);
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Category created successfully.");
//            return ResponseEntity.ok(response);
//        }


    @PutMapping("/{CategoryID}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id,@RequestBody @Valid Category categoryUpdateRequest){
        Category category = this.categoryService.updateCategory(id,categoryUpdateRequest);
        return ResponseEntity.ok("Category updated successfully.");
    }

    @DeleteMapping("/{categoryID}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryID){
        this.categoryService.deleteCategory(categoryID);
        return ResponseEntity.ok("Category Deleted Successfully.");
    }
}
