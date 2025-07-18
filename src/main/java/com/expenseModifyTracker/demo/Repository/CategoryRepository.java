package com.expenseModifyTracker.demo.Repository;

import com.expenseModifyTracker.demo.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
