package com.expenseModifyTracker.demo.dto;

import com.expenseModifyTracker.demo.Model.User;


import java.util.Date;

public class UserExpenseDTO {

    private Long expenseID;
    private String title;
    private double amount;
    private Date date;
    private Long userID;
    private Long categoryID;
    private String categoryName;

    public UserExpenseDTO() {
    }

    public UserExpenseDTO(Long expenseID, String title, double amount, Date date, Long userID, Long categoryID, String categoryName) {
        this.expenseID = expenseID;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.userID = userID;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public Long getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(Long expenseID) {
        this.expenseID = expenseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
