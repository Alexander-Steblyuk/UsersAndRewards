package com.example.users_and_rewards.exceptions.reward_service_exceptions;

public class InvalidRewardTitleException extends RewardServiceException {
    private static final String INVALID_TITLE_INSERT_MESSAGE = "TITLE CAN`T BE NULL OR EMPTY!";

    public InvalidRewardTitleException() {
        super();
    }

    @Override
    public String getMessage() {
        return INVALID_TITLE_INSERT_MESSAGE;
    }
}
