//package com.expenseModifyTracker.demo.Repository;
//
//import com.expenseModifyTracker.demo.Model.User;
//import com.expenseModifyTracker.demo.Model.UserExpense;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//
//public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {
////    public List<UserExpense> findByUser(User user);
//
////    List<UserExpense> findByUserId(Long userID); // Get expenses by user
////    List<UserExpense> findByUser_UserIDAndExpenseDateBetween(Long userID, LocalDate startDate, LocalDate endDate);}
//
//
//    List<UserExpense> findByUser_UserID(Long userID);
//
//    List<UserExpense> findByUser_UserIDAndExpenseDateBetween(
//            Long userID, LocalDate start, LocalDate end
//    );  }


package com.expenseModifyTracker.demo.Repository;

import com.expenseModifyTracker.demo.Model.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {

    // For all expenses by user ID
    List<UserExpense> findByUser_UserID(Long userID);

    // For expenses by user ID within a date range
    List<UserExpense> findByUser_UserIDAndDateBetween(Long userID, LocalDate startDate, LocalDate endDate);

}
