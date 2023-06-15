package com.example.users_and_rewards.services;

import com.example.users_and_rewards.repositories.RewardRepository;
import com.example.users_and_rewards.repositories.RewardingRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardService {
    private RewardRepository rewardRepository;
    private RewardingRepository rewardingRepository;

    @Autowired
    public void setRewardRepository(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @Autowired
    public void setRewardingRepository(RewardingRepository rewardingRepository) {
        this.rewardingRepository = rewardingRepository;
    }


}
