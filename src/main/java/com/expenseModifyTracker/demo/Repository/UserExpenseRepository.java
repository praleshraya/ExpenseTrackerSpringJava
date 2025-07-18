package com.expenseModifyTracker.demo.Repository;

import com.expenseModifyTracker.demo.Model.User;
import com.expenseModifyTracker.demo.Model.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {
    public List<UserExpense> findByUser(User user);
}
