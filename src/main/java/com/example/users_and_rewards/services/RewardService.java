package com.example.users_and_rewards.services;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.exceptions.reward_service_exceptions.InvalidRewardTitleException;
import com.example.users_and_rewards.exceptions.reward_service_exceptions.RewardAlreadyExistsException;
import com.example.users_and_rewards.exceptions.reward_service_exceptions.RewardServiceException;
import com.example.users_and_rewards.repositories.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardService {
    private static final String EMPTY_STRING = "";

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

    public List<Reward> getRewards(String title, String description) {
        List<Reward> rewards;

        if (title == null && description == null) {
            rewards = rewardRepository.findAll();
        } else {
            rewards = rewardRepository.findFilteredRewards(title, description);
        }

        return rewards;
    }

    public Reward getRewardByTitle(String title) throws RewardServiceException {
        return rewardRepository.findRewardByTitle(title);
    }

    public void edit(Reward reward) throws RewardServiceException {
        if (reward.getTitle() == null || reward.getTitle().equals(EMPTY_STRING)) {
            throw new InvalidRewardTitleException();
        }

        if (rewardRepository.findRewardByTitle(reward.getTitle()) != null) {
            throw new RewardAlreadyExistsException();
        }

        try {
            rewardRepository.save(reward);
        } catch (Exception e) {
            throw new RewardServiceException(e.getMessage(), e);
        }
    }

    public void update(Reward reward) throws RewardServiceException {
        try {
            Reward oldReward = rewardRepository.findRewardByTitle(reward.getTitle());
            rewardRepository.update(oldReward.getTitle(), reward.getTitle(), reward.getDescription());
        } catch (Exception e) {
            throw new RewardServiceException(e.getMessage(), e);
        }
    }

    public void delete(Reward reward) throws RewardServiceException {
        try {
            rewardRepository.delete(reward);
        } catch (Exception e) {
            throw new RewardServiceException(e.getMessage(), e);
        }
    }

    public List<Rewarding> findRewardingsByReward(Reward reward) {
        return rewardingService.findRewardingByReward(reward);
    }
}
