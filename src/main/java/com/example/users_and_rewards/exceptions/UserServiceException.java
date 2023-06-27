package com.example.users_and_rewards.exceptions;

public class UserServiceException extends IllegalArgumentException {
    public UserServiceException(Throwable cause) {
        super(cause);
    }

    public UserServiceException(String s) {
        super(s);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
