package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.exceptions.user_service_exceptions.*;
import com.example.users_and_rewards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String showAllUsers(Model model, @RequestParam(name = "fullName", required = false) String fullName,
                        @RequestParam(name = "birthday", required = false) LocalDate birthday) {

        model.addAttribute("fullName", fullName);
        model.addAttribute("birthday", birthday);
        model.addAttribute("users", userService.getUsers(fullName, birthday));

        return "users";
    }

    @GetMapping("/show/{id}")
    public String showUser(Model model, @PathVariable("id") Long id) {
        try {
            model.addAttribute("user", userService.getUserById(id));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        return "user-page";
    }

    @GetMapping("/edit")
    public String addUser(Model model) {
        model.addAttribute("user", new User());

        return "edit-user";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("user", userService.getUserById(id));

        return "edit-user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(userService.getUserById(id));

        return "redirect:/users";
    }

    @PostMapping("/edit")
    public String addUser(@ModelAttribute(value = "user") User user) {
        try {
            userService.edit(user);
        } catch (IllegalFirstNameException | IllegalLastNameException |
                IllegalBirthDayException | UserAlreadyExistsException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return "redirect:/users";
    }
}