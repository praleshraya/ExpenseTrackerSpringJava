package com.expenseModifyTracker.demo.Repository;

import com.expenseModifyTracker.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserNameAndUserPassword(String userName, String userPassword);
}
