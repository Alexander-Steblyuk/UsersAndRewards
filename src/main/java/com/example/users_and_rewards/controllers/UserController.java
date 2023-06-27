package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String showAllUsers(Model model, @RequestParam(name = "fullName", required = false) String fullName,
                        @RequestParam(name = "birthday", required = false) LocalDate birthday) {

        model.addAttribute("fullName", fullName);
        model.addAttribute("birthday", birthday);
        model.addAttribute("users", userService.getAllUsers(fullName, birthday));

        return "users";
    }
}
