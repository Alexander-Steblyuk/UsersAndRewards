package com.example.users_and_rewards.exceptions.reward_service_exceptions;

public class RewardAlreadyExistsException extends RewardServiceException {
    private static final String EXISTING_REWARD_INSERT_MESSAGE = "THE REWARD WITH THIS TITLE HAS ALREADY EXIST!";

    public RewardAlreadyExistsException() {
        super();
    }

    @Override
    public String getMessage() {
        return EXISTING_REWARD_INSERT_MESSAGE;
    }
}
