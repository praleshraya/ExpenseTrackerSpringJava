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

    //update profile
    public UserDTO updateUserProfile(Long userId, UserDTO updatedUser) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        user.setUserName(updatedUser.getUserName());
        user.setUserEmail(updatedUser.getUserEmail());

        User saved = userRepository.save(user);
        return UserMapper.mapUserToUserDTO(saved);
    }

   //change password
    public String changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        if (!user.getUserPassword().equals(oldPassword)) {
            throw new RuntimeException("Old password does not match");
        }

        user.setUserPassword(newPassword);
        userRepository.save(user);
        return "Password changed successfully";
    }
}
