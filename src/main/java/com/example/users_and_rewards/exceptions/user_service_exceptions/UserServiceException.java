package com.example.users_and_rewards.exceptions.user_service_exceptions;

public class UserServiceException extends IllegalArgumentException {
    public UserServiceException() {
        super();
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
