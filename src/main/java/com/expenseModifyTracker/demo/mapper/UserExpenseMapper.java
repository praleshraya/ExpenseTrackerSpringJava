package com.expenseModifyTracker.demo.mapper;

import com.expenseModifyTracker.demo.Model.User;
import com.expenseModifyTracker.demo.Model.UserExpense;
import com.expenseModifyTracker.demo.dto.UserExpenseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserExpenseMapper {

    public static UserExpenseDTO mapUserExpeneseToUserExpenseDTO(UserExpense userExpense)
    {
        return new UserExpenseDTO(userExpense.getExpenseID(),
                userExpense.getTitle(),
                userExpense.getAmount(),
                userExpense.getDate(),
                userExpense.getUser().getUserID(),
                userExpense.getCategory().getCategoryID(),
                userExpense.getCategory().getCategoryName()
                );
    }

    public static List<UserExpenseDTO> UserExpenseDTOList(List<UserExpense> expenses) {
        return expenses.stream().map(UserExpenseMapper::mapUserExpeneseToUserExpenseDTO).collect(Collectors.toList());
    }


}
