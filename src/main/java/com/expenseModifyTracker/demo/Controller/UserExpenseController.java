package com.expenseModifyTracker.demo.Controller;

import com.expenseModifyTracker.demo.Model.UserExpense;
import com.expenseModifyTracker.demo.Service.UserExpenseService;
import com.expenseModifyTracker.demo.dto.UserExpenseDTO;
import com.expenseModifyTracker.demo.mapper.UserExpenseMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/expenses")
public class UserExpenseController {
    private final UserExpenseService userExpenseService;

    @Autowired
    public UserExpenseController(UserExpenseService userExpenseService){
        this.userExpenseService = userExpenseService;
    }

    //url http://localhost:8080/expenses/users/1


    @GetMapping("/users/{userID}")
    public ResponseEntity<List<UserExpenseDTO>> getExpensesByUser(@PathVariable Long userID){

        List<UserExpense> userExpenses = this.userExpenseService.getAllExpensesByUser(userID);
        return ResponseEntity.ok(UserExpenseMapper.UserExpenseDTOList(userExpenses));
    }

    //url http://localhost:8080/expenses
    @PostMapping
    public ResponseEntity<UserExpenseDTO> createUserExpense( @RequestBody @Valid UserExpense userExpenseRequest){
        if (userExpenseRequest.getUser() == null) {
            throw new RuntimeException("User is not set for the expense");
        }
        System.err.println("Successfully inside createUserExpense");
        UserExpense userExpense = this.userExpenseService.createUserExpense(userExpenseRequest);
        return ResponseEntity.ok(UserExpenseMapper.mapUserExpeneseToUserExpenseDTO(userExpense));
    }
    //url http://localhost:8080/expenses/users/expenseID
    @PutMapping("/users/{expenseID}")
    public ResponseEntity<UserExpenseDTO> updateUserExpense(@PathVariable Long expenseID, @RequestBody @Valid UserExpense userExpenseRequest){
        userExpenseRequest.setExpenseID(expenseID);

        UserExpense updatedUserExpense = this.userExpenseService.updateUserExpense(userExpenseRequest);

        return ResponseEntity.ok(UserExpenseMapper.mapUserExpeneseToUserExpenseDTO(updatedUserExpense));
    }

    @DeleteMapping("/users/{expenseID}")
    public ResponseEntity<String> deleteUserExpense(@PathVariable Long expenseID){
        this.userExpenseService.deleteExpense(expenseID);
        return ResponseEntity.ok("Expense deleted successfully.");
    }
}
