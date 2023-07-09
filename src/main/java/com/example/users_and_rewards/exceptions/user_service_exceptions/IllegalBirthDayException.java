package com.example.users_and_rewards.exceptions.user_service_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "INVALID BIRTHDAY VALUE!")
public class IllegalBirthDayException extends UserServiceException {
    private static final String INVALID_BIRTHDAY_INSERT_MESSAGE = "INVALID BIRTHDAY VALUE!";

    public IllegalBirthDayException(Throwable cause) {
        super(cause);
    }

    public IllegalBirthDayException(String s) {
        super(s);
    }

    public IllegalBirthDayException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalBirthDayException() {
        super(INVALID_BIRTHDAY_INSERT_MESSAGE);
    }

    @Override
    public String getMessage() {
        return INVALID_BIRTHDAY_INSERT_MESSAGE;
    }
}
