package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.exceptions.user_service_exceptions.UserServiceException;
import com.example.users_and_rewards.exceptions.user_service_exceptions.UserNotFoundException;
import com.example.users_and_rewards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String FULL_NAME_ATTRIBUTE = "fullName";
    private static final String BIRTHDAY_ATTRIBUTE = "birthday";
    private static final String USER_ATTRIBUTE = "user";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String REWARDINGS_ATTRIBUTE = "rewardings";

    private static final String ID_PATH_VARIABLE = "id";

    private static final String USERS_PAGE_PATH = "users_tmpl/users";
    private static final String EDIT_USER_PAGE_PATH = "users_tmpl/edit-user";
    private static final String USER_PAGE_PATH = "users_tmpl/user-page";
    private static final String REDIRECT_USERS_PAGE_PATH = "redirect:/users";

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(Model model, @RequestParam(name = FULL_NAME_ATTRIBUTE, required = false) String fullName,
                        @RequestParam(name = BIRTHDAY_ATTRIBUTE, required = false) LocalDate birthday) {

        model.addAttribute(FULL_NAME_ATTRIBUTE, fullName);
        model.addAttribute(BIRTHDAY_ATTRIBUTE, birthday);
        model.addAttribute(USERS_ATTRIBUTE, userService.getUsers(fullName, birthday));

        return USERS_PAGE_PATH;
    }

    @GetMapping("/show/{id}")
    public String showUser(Model model, @PathVariable(ID_PATH_VARIABLE) Long id) {
        try {
            User user = userService.getUserById(id);

            model.addAttribute(USERS_ATTRIBUTE, user);
            model.addAttribute(REWARDINGS_ATTRIBUTE, userService.findRewardings(user));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return USER_PAGE_PATH;
    }

    @GetMapping("/edit")
    public String addUser(Model model) {
        model.addAttribute(USER_ATTRIBUTE, new User());

        return EDIT_USER_PAGE_PATH;
    }

    @PostMapping("/edit")
    public String addUser(@ModelAttribute(value = USER_ATTRIBUTE) User user) {
        try {
            userService.edit(user);
        } catch (UserServiceException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return REDIRECT_USERS_PAGE_PATH;
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable(name = ID_PATH_VARIABLE) Long id) {
        try {
            model.addAttribute(USER_ATTRIBUTE, userService.getUserById(id));
        } catch (UserServiceException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return EDIT_USER_PAGE_PATH;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(ID_PATH_VARIABLE) Long id) {
        try {
            userService.delete(userService.getUserById(id));
        } catch (UserServiceException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return REDIRECT_USERS_PAGE_PATH;
    }
}