package com.example.users_and_rewards.exceptions.user_service_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "THIS USER IS NOT EXISTS!")
public class UserNotFoundException extends UserServiceException {
    private static final String USER_NOT_FOUND_MESSAGE = "THIS USER IS NOT EXISTS!";

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String s) {
        super(s);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE);
    }

    @Override
    public String getMessage() {
        return USER_NOT_FOUND_MESSAGE;
    }
}
