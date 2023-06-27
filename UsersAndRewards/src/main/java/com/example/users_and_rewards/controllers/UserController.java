package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    void doSomething() {
        User user = new User("Sasha", "Stebelkov", LocalDate.of(2023, 1, 1));
        userService.save(new User("Sasha", "Stebelkov", LocalDate.of(2023, 1, 1)));
        userService.save(user);
    }
}
