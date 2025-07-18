package com.expenseModifyTracker.demo.Service;

import com.expenseModifyTracker.demo.Model.User;
import com.expenseModifyTracker.demo.Repository.UserRepository;
import com.expenseModifyTracker.demo.dto.UserDTO;
import com.expenseModifyTracker.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public User createUser(User user){
        return this.userRepository.save(user);
    }

    public User loginUser(User user){

        User validatedUser= this.userRepository.findByUserNameAndUserPassword(user.getUserName(), user.getUserPassword());

        if(validatedUser !=null)
        {
            return validatedUser;
        }
        return user;
    }
}
