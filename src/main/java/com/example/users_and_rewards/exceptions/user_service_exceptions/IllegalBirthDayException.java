package com.example.users_and_rewards.exceptions.user_service_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "INVALID BIRTHDAY VALUE!")
public class IllegalBirthDayException extends UserServiceException {
    private static final String INVALID_BIRTHDAY_INSERT_MESSAGE = "INVALID BIRTHDAY VALUE!";

    @Override
    public String getMessage() {
        return INVALID_BIRTHDAY_INSERT_MESSAGE;
    }
}
