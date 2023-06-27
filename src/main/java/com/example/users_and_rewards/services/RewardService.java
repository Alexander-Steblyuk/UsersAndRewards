package com.example.users_and_rewards.services;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.exceptions.RewardServiceException;
import com.example.users_and_rewards.repositories.RewardRepository;
import com.example.users_and_rewards.repositories.RewardingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardService {
    private static final String EMPTY_STRING = "";

    private static final String INVALID_TITLE_INSERT_MESSAGE = "TITLE CAN`T BE NULL OR EMPTY!";
    private static final String EXISTING_REWARD_INSERT_MESSAGE = "THE REWARD WITH THIS TITLE HAS ALREADY EXIST!";

    private RewardRepository rewardRepository;
    private RewardingService rewardingService;

    @Autowired
    public void setRewardRepository(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @Autowired
    public void setRewardingService(RewardingService rewardingService) {
        this.rewardingService = rewardingService;
    }

    public void save(Reward reward) throws RewardServiceException {
        String message = EMPTY_STRING;
        boolean isException = true;

        if (reward.getTitle() == null || reward.getTitle().equals(EMPTY_STRING)) {
            message = INVALID_TITLE_INSERT_MESSAGE;
        } else if (rewardRepository.findRewardByTitle(reward.getTitle()) != null) {
            message = EXISTING_REWARD_INSERT_MESSAGE;
        } else {
            isException = false;
        }

        if (isException) {
            throw new RewardServiceException(message);
        }

        try {
            rewardRepository.save(reward);
        } catch (Exception e) {
            throw new RewardServiceException(e.getMessage());
        }
    }

    public void update(Reward reward) throws RewardServiceException {
        Reward oldReward = rewardRepository.findRewardByTitle(reward.getTitle());

        try {
            if (!oldReward.getTitle().equals(reward.getTitle())) {
                rewardRepository.updateTitle(reward.getTitle(), reward.getTitle());
            }
            if (!oldReward.getDescription().equals(reward.getDescription())) {
                rewardRepository.updateDescription(reward.getTitle(), reward.getDescription());
            }
        } catch (Exception e) {
            throw new RewardServiceException(e.getMessage());
        }
    }

    public void delete(Reward reward) throws RewardServiceException {
        try {
            rewardRepository.delete(reward);
        } catch (Exception e) {
            throw new RewardServiceException(e.getMessage());
        }
    }

    public List<Rewarding> findRewardingsByReward(Reward reward) {
        return rewardingService.findRewardingByReward(reward);
    }
}
