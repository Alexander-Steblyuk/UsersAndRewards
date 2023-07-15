package com.example.users_and_rewards.exceptions.user_service_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "LAST NAME CAN`T BE NULL OR EMPTY!")
public class IllegalLastNameException extends UserServiceException {
    private static final String INVALID_LASTNAME_INSERT_MESSAGE = "LAST NAME CAN`T BE NULL OR EMPTY!";

    @Override
    public String getMessage() {
        return INVALID_LASTNAME_INSERT_MESSAGE;
    }
}
