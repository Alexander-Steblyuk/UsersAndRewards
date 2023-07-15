package com.example.users_and_rewards.exceptions.rewarding_service_exceptions;

public class RewardingServiceException extends IllegalArgumentException {
    public RewardingServiceException() {
        super();
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
