package com.example.users_and_rewards.exceptions.user_service_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "USER WITH SAME PARAMS HAS ALREADY EXISTS!")
public class UserAlreadyExistsException extends UserServiceException {
    private static final String EXISTING_USER_INSERT_MESSAGE = "USER WITH SAME PARAMS HAS ALREADY EXISTS!";

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExistsException(String s) {
        super(s);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException() {
        super(EXISTING_USER_INSERT_MESSAGE);
    }

    @Override
    public String getMessage() {
        return EXISTING_USER_INSERT_MESSAGE;
    }
}
