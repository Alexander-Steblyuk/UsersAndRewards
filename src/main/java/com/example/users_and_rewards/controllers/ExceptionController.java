package com.example.users_and_rewards.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController implements ErrorController {
    private static final String STATUS_ATTRIBUTE_NAME = "status";
    private static final String MESSAGE_ATTRIBUTE_NAME = "message";
    private static final String ERROR_PAGE_PATH = "/error";

    @RequestMapping(ERROR_PAGE_PATH)
    public String handle(Model model, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        model.addAttribute(STATUS_ATTRIBUTE_NAME, status);
        model.addAttribute(MESSAGE_ATTRIBUTE_NAME, message);

        return "error";
    }
}
