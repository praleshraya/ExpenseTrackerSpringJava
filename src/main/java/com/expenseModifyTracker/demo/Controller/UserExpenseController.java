package com.expenseModifyTracker.demo.Controller;

import com.expenseModifyTracker.demo.Model.User;
import com.expenseModifyTracker.demo.Model.UserExpense;
import com.expenseModifyTracker.demo.Service.UserExpenseService;
import com.expenseModifyTracker.demo.dto.UserExpenseDTO;
import com.expenseModifyTracker.demo.mapper.UserExpenseMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
@CrossOrigin( origins = "http://localhost:4200/")
public class UserExpenseController {
    private final UserExpenseService userExpenseService;

    @Autowired
    public UserExpenseController(UserExpenseService userExpenseService){
        this.userExpenseService = userExpenseService;
    }

    //url http://localhost:8080/expenses/users/1


//    @GetMapping("/users/{userID}")
//    public ResponseEntity<List<UserExpenseDTO>> getExpensesByUser(@PathVariable Long userID){
//        System.err.println("inside get all expenses by user function - Spring Boot Backend.");
//
//        List<UserExpense> userExpenses = this.userExpenseService.getAllExpensesByUser(userID);
//        return ResponseEntity.ok(UserExpenseMapper.UserExpenseDTOList(userExpenses));
//    }

    @GetMapping("/users/{userID}")
    public ResponseEntity<List<UserExpenseDTO>> getExpensesByUser(@PathVariable Long userID) {
        List<UserExpense> userExpenses = userExpenseService.getAllExpensesByUser(userID);
        return ResponseEntity.ok(UserExpenseMapper.UserExpenseDTOList(userExpenses));
    }

    // Get Total Expenses
    @GetMapping("/total/{userId}")
    public ResponseEntity<Double> getTotalExpenses(@PathVariable Long userId) {
        Double total = userExpenseService.getTotalExpenses(userId);
        return ResponseEntity.ok(total);
    }
    @GetMapping("/category/{userId}")
    public ResponseEntity<Map<String, Double>> getExpenseByCategory(@PathVariable Long userId) {
        Map<String, Double> categoryTotals = userExpenseService.getExpenseByCategory(userId);
        return ResponseEntity.ok(categoryTotals);
    }

    // Get Expenses by Date (Day, Month, Year)
    @GetMapping("/date/{userId}")
    public ResponseEntity<Map<String, Double>> getExpenseByDate(@PathVariable Long userId) {
        Map<String, Double> dateTotals = userExpenseService.getExpenseByDate(userId);
        return ResponseEntity.ok(dateTotals);
    }

    // Get Expenses by Custom Date Range
    @GetMapping("/range/{userId}")
    public ResponseEntity<Map<String, Double>> getExpenseByRange(@PathVariable Long userId,
                                                                 @RequestParam LocalDate startDate,
                                                                 @RequestParam LocalDate endDate) {
        // Calling the service method to get expense totals in the given range
        Map<String, Double> dateRangeTotals = userExpenseService.findByUser_UserIDAndDateBetween(userId, startDate, endDate);
        return ResponseEntity.ok(dateRangeTotals); // Returning the totals as response
    }

    //url http://localhost:8080/expenses
    @PostMapping("/add")
    public ResponseEntity<UserExpenseDTO> createUserExpense( @RequestBody @Valid UserExpense userExpenseRequest){
        System.err.println("inside create expense function - Spring Boot Backend.");
        if (userExpenseRequest.getUser() == null || userExpenseRequest.getUser().getUserID()==null) {
            throw new RuntimeException("User is not set for the expense");
        }
//        if (userExpenseRequest.getUser().getUserID() == null) {
//            throw new RuntimeException("User cannot be null");
//        }
        System.err.println("Successfully inside createUserExpense");
        UserExpense userExpense = this.userExpenseService.createUserExpense(userExpenseRequest);
        return ResponseEntity.ok(UserExpenseMapper.mapUserExpeneseToUserExpenseDTO(userExpense));
    }
    //url http://localhost:8080/expenses/users/expenseID
    @PutMapping("/users/{expenseID}")
    public ResponseEntity<UserExpenseDTO> updateUserExpense(@PathVariable Long expenseID, @RequestBody @Valid UserExpense userExpenseRequest){
        System.err.println("inside update function - Spring Boot Backend.");
        userExpenseRequest.setExpenseID(expenseID);

        UserExpense updatedUserExpense = this.userExpenseService.updateUserExpense(userExpenseRequest);

        return ResponseEntity.ok(UserExpenseMapper.mapUserExpeneseToUserExpenseDTO(updatedUserExpense));
    }
    //url http://localhost:8080/expenses/users/expenseID
    @DeleteMapping("/users/{expenseID}")
    public ResponseEntity<String> deleteUserExpense(@PathVariable Long expenseID){
        System.err.println("inside delete function - Spring Boot Backend.");
        this.userExpenseService.deleteExpense(expenseID);
        return ResponseEntity.ok("Expense deleted successfully.");
    }
}
