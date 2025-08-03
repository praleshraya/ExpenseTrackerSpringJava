package com.expenseModifyTracker.demo.Service;

import com.expenseModifyTracker.demo.Model.Category;
import com.expenseModifyTracker.demo.Model.User;
import com.expenseModifyTracker.demo.Model.UserExpense;
import com.expenseModifyTracker.demo.Repository.CategoryRepository;
import com.expenseModifyTracker.demo.Repository.UserExpenseRepository;
import com.expenseModifyTracker.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    // Get Expense by Category
    public Map<String, Double> getExpenseByCategory(Long userID) {
        List<UserExpense> expenses = userExpenseRepository.findByUser_UserID(userID);
        Map<String, Double> categoryTotals = new HashMap<>();
        for (UserExpense expense : expenses) {
            String categoryName = expense.getCategory().getCategoryName();
            categoryTotals.put(categoryName, categoryTotals.getOrDefault(categoryName, 0.0) + expense.getAmount());
        }
        return categoryTotals;
    }

    public Map<String, Double> getExpenseByDate(Long userID) {
        List<UserExpense> expenses = userExpenseRepository.findByUser_UserID(userID);
        Map<String, Double> dateTotals = new HashMap<>();

        double totalToday = 0.0;
        double totalThisMonth = 0.0;
        double totalThisYear = 0.0;

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfYear = today.withDayOfYear(1);

        for (UserExpense expense : expenses) {
            LocalDate date = expense.getDate();
            if (date == null) continue;

            if (date.equals(today)) {
                totalToday += expense.getAmount();
            }
            if (!date.isBefore(firstDayOfMonth)) {
                totalThisMonth += expense.getAmount();
            }
            if (!date.isBefore(firstDayOfYear)) {
                totalThisYear += expense.getAmount();
            }
        }

        dateTotals.put("Today", totalToday);
        dateTotals.put("This Month", totalThisMonth);
        dateTotals.put("This Year", totalThisYear);

        return dateTotals;
    }




    // Get Total Expenses
    public Double getTotalExpenses(Long userID) {
        List<UserExpense> expenses = userExpenseRepository.findByUser_UserID(userID);
        return expenses.stream().mapToDouble(UserExpense::getAmount).sum();
    }

    // Get Expenses by Date Range (for specific time periods like today, month, or year)
    public Map<String, Double> findByUser_UserIDAndDateBetween(Long userID, LocalDate startDate, LocalDate endDate) {
        List<UserExpense> expenses = userExpenseRepository.findByUser_UserIDAndDateBetween(userID, startDate, endDate);
        Map<String, Double> dateRangeTotals = new HashMap<>();
        double total = 0.0;
        for (UserExpense expense : expenses) {
            total += expense.getAmount();
        }
        dateRangeTotals.put("Total in Date Range", total);
        return dateRangeTotals; // Returns a map with total expense in the date range
    }


    public List<UserExpense> getAllExpensesByUser(Long userID){
        Optional<User> user = this.userRepository.findById(userID);
        if(user.isPresent()){
            return this.userExpenseRepository.findByUser_UserID(user.get().getUserID());
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    public UserExpense createUserExpense(UserExpense userExpense){
        User user = this.userRepository.findById(userExpense.getUser().getUserID()).orElseThrow(() -> new RuntimeException("User not found"));
        Category category = this.categoryRepository.findById(userExpense.getCategory().getCategoryID()).orElseThrow(() -> new RuntimeException(("Category not found")));

        userExpense.setUser(user);
        userExpense.setCategory(category);


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
