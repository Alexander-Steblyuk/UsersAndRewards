package com.example.users_and_rewards.exceptions.reward_service_exceptions;

public class RewardServiceException extends IllegalArgumentException {
    public RewardServiceException() {
        super();
    }

    public RewardServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
