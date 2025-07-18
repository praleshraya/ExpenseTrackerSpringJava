package com.expenseModifyTracker.demo.Service;

import com.expenseModifyTracker.demo.Model.Category;
import com.expenseModifyTracker.demo.Model.User;
import com.expenseModifyTracker.demo.Model.UserExpense;
import com.expenseModifyTracker.demo.Repository.CategoryRepository;
import com.expenseModifyTracker.demo.Repository.UserExpenseRepository;
import com.expenseModifyTracker.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserExpenseService {
    private final UserExpenseRepository userExpenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public UserExpenseService(UserExpenseRepository userExpenseRepository, UserRepository userRepository, CategoryRepository categoryRepository){
        this.userExpenseRepository = userExpenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<UserExpense> getAllExpensesByUser(Long userID){
        Optional<User> user = this.userRepository.findById(userID);
        if(user.isPresent()){
            return this.userExpenseRepository.findByUser(user.get());
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    public UserExpense createUserExpense(UserExpense userExpense){
        User user = this.userRepository.findById(userExpense.getUser().getUserID()).orElseThrow(() -> new RuntimeException("User not found"));
        Category category = this.categoryRepository.findById(userExpense.getCategory().getCategoryID()).orElseThrow(() -> new RuntimeException(("Category not found")));

        userExpense.setUser(user);
        userExpense.setCategory(category);
        if (userExpense.getUser() == null) {
            userExpense.setUser(user);
        }

        return this.userExpenseRepository.save(userExpense);
    }

    public UserExpense updateUserExpense(UserExpense updatedExpense){
        UserExpense existingExpense = this.userExpenseRepository.findById(updatedExpense.getExpenseID())
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        User user = this.userRepository.findById(updatedExpense.getUser().getUserID())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = this.categoryRepository.findById(updatedExpense.getCategory().getCategoryID())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingExpense.setUser(user);
        existingExpense.setCategory(category);
        existingExpense.setTitle(updatedExpense.getTitle());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setDate(updatedExpense.getDate());

        return this.userExpenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long expenseId){
        if (!this.userExpenseRepository.existsById(expenseId)) {
            throw new RuntimeException("Expense not found");
        }
        this.userExpenseRepository.deleteById(expenseId);
    }
}
