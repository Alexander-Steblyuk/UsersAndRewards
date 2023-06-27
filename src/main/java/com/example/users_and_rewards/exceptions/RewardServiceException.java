package com.example.users_and_rewards.exceptions;

public class RewardServiceException extends IllegalArgumentException {
    public RewardServiceException(Throwable cause) {
        super(cause);
    }

    public RewardServiceException(String s) {
        super(s);
    }

    public RewardServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
