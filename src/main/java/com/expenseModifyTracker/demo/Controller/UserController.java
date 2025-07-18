package com.expenseModifyTracker.demo.Controller;

import com.expenseModifyTracker.demo.Model.User;
import com.expenseModifyTracker.demo.Service.UserService;
import com.expenseModifyTracker.demo.dto.UserDTO;
import com.expenseModifyTracker.demo.dto.UserLoginRequestDTO;
import com.expenseModifyTracker.demo.dto.UserLoginResponseDTO;
import com.expenseModifyTracker.demo.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:4200/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody @Valid User userRequest) {
        User user = this.userService.createUser(userRequest);
        if (user != null) {
            UserMapper.mapUserToUserDTO(user);
            return ResponseEntity.ok("User created Successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User cannot be created.");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserLoginResponseDTO> loginUser(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO) {
//        Optional<User> user = this.userService.loginUser(userLoginRequestDTO.getUserName(), userLoginRequestDTO.getUserPassword());
////        if (user.isPresent()) {
////
////            return ResponseEntity.ok("User Logged in.");
////        }
////        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UserMapper.mapUserToUserLoginResponseDTO(user, "User not found or incorrect username or password."));
//        // }
//
//        if ( != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
//        }
    //}
// localhost:8080/users/login
//@PostMapping("/login")
//public ResponseEntity<UserLoginResponseDTO> loginUser(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO) {
//    System.err.println("checking valid user...");
//    Optional<User> userOptional = userService.loginUser(userLoginRequestDTO.getUserName(), userLoginRequestDTO.getUserPassword());
//
//    if (userOptional.isPresent()) {
//        User user = userOptional.get();
//        UserLoginResponseDTO responseDTO = UserMapper.mapUserToUserLoginResponseDTO(user, "Login successful.");
//        return ResponseEntity.ok(responseDTO);
//    } else
//    {
//        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
//        responseDTO.setMessage("Invalid username or password.");
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
//    }
//}

@PostMapping("/login")
public ResponseEntity<UserLoginResponseDTO> loginUser(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO) {
    // Convert DTO to User object
    User loginAttempt = new User();
    loginAttempt.setUserName(userLoginRequestDTO.getUserName());
    loginAttempt.setUserPassword(userLoginRequestDTO.getUserPassword());
    System.err.println("checking valid user...");
    User validatedUser = userService.loginUser(loginAttempt);

    if (validatedUser != null) {
        UserLoginResponseDTO responseDTO = UserMapper.mapUserToUserLoginResponseDTO(validatedUser, "Login successful.");
        return ResponseEntity.ok(responseDTO);
    } else {
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
        responseDTO.setMessage("Invalid username or password.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }
}
}
