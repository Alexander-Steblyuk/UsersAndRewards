package com.example.users_and_rewards.exceptions.reward_service_exceptions;

public class RewardAlreadyExistsException extends RewardServiceException {
    private static final String EXISTING_REWARD_INSERT_MESSAGE = "THE REWARD WITH THIS TITLE HAS ALREADY EXIST!";

    public RewardAlreadyExistsException() {
        super();
    }

    public RewardAlreadyExistsException(String s) {
        super(s);
    }

    public RewardAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RewardAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return EXISTING_REWARD_INSERT_MESSAGE;
    }
}
