package com.example.users_and_rewards.exceptions;

public class RewardingServiceException extends IllegalArgumentException {
    public RewardingServiceException() {
    }

    public RewardingServiceException(String s) {
        super(s);
    }

    public RewardingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RewardingServiceException(Throwable cause) {
        super(cause);
    }
}
