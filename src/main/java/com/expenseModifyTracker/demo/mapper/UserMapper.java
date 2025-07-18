package com.expenseModifyTracker.demo.mapper;

import com.expenseModifyTracker.demo.Model.User;
import com.expenseModifyTracker.demo.Model.UserExpense;
import com.expenseModifyTracker.demo.dto.UserDTO;
import com.expenseModifyTracker.demo.dto.UserExpenseDTO;
import com.expenseModifyTracker.demo.dto.UserLoginResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO mapUserToUserDTO(User user){
        return new UserDTO(
                user.getUserID(),
                user.getUserName(),
                user.getUserEmail()
        );
    }

    public static UserLoginResponseDTO mapUserToUserLoginResponseDTO(User user, String message){
        return new UserLoginResponseDTO(
                user.getUserID(),
                user.getUserName(),
                user.getUserEmail(),
                message
        );
    }


//    public static List<UserDTO> UserDTOList(List<User> users) {
//        return users.stream().map(UserMapper::mapUserToUserDTO).collect(Collectors.toList());
//    }
}
