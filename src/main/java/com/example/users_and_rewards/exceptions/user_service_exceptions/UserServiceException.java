package com.example.users_and_rewards.exceptions.user_service_exceptions;

public class UserServiceException extends IllegalArgumentException {
    public UserServiceException() {
        super();
    }

    public UserServiceException(String s) {
        super(s);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }
}
