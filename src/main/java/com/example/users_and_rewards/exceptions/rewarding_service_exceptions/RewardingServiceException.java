package com.example.users_and_rewards.exceptions.rewarding_service_exceptions;

import java.util.function.Supplier;

public class RewardingServiceException extends IllegalArgumentException {
    public RewardingServiceException(String s) {
        super(s);
    }
}
