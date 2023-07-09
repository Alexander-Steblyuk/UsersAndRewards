package com.example.users_and_rewards.exceptions.user_service_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "FIRST NAME CAN`T BE NULL OR EMPTY!")
public class IllegalFirstNameException extends UserServiceException {
    private static final String INVALID_FIRSTNAME_INSERT_MESSAGE = "FIRST NAME CAN`T BE NULL OR EMPTY!";

    public IllegalFirstNameException(Throwable cause) {
        super(cause);
    }

    public IllegalFirstNameException(String s) {
        super(s);
    }

    public IllegalFirstNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalFirstNameException() {
        super(INVALID_FIRSTNAME_INSERT_MESSAGE);
    }

    @Override
    public String getMessage() {
        return INVALID_FIRSTNAME_INSERT_MESSAGE;
    }
}
