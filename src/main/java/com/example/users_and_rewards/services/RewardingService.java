package com.example.users_and_rewards.services;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.exceptions.RewardingServiceException;
import com.example.users_and_rewards.repositories.RewardingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RewardingService {
    private RewardingRepository rewardingRepository;

    @Autowired
    public void setRewardingRepository(RewardingRepository rewardingRepository) {
        this.rewardingRepository = rewardingRepository;
    }

    public void save(User user, Reward reward, LocalDate date)  throws RewardingServiceException {
        try {
            rewardingRepository.save(user.getId(), reward.getTitle(), date);
        } catch (Exception e) {
            throw new RewardingServiceException(e.getMessage());
        }
    }

    public void update(User user, Reward reward, LocalDate date) throws RewardingServiceException {
        Rewarding oldRewarding = rewardingRepository.findRewardingByIdUserAndIdRewardAndIdRewardingDate(user, reward, date);

        try {
            if (!oldRewarding.getUser().getId().equals(user.getId())) {
                rewardingRepository.updateUser(oldRewarding.getUser().getId(), oldRewarding.getReward().getTitle(),
                        oldRewarding.getRewardingDate(), user.getId());
            }
            if (!oldRewarding.getReward().getTitle().equals(reward.getTitle())) {
                rewardingRepository.updateReward(oldRewarding.getUser().getId(), oldRewarding.getReward().getTitle(),
                        oldRewarding.getRewardingDate(), reward.getTitle());
            }
            if (!oldRewarding.getRewardingDate().equals(date)) {
                rewardingRepository.updateDate(oldRewarding.getUser().getId(), oldRewarding.getReward().getTitle(),
                        oldRewarding.getRewardingDate(), date);
            }
        } catch (Exception e) {
            throw new RewardingServiceException(e.getMessage());
        }
    }

    public void delete(User user, Reward reward, LocalDate date) throws RewardingServiceException {
        try {
            rewardingRepository.delete(user.getId(), reward.getTitle(), date);
        } catch (Exception e) {
            throw new RewardingServiceException(e.getMessage());
        }
    }

    public List<Rewarding> findRewardingByUser(User user) {
        return rewardingRepository.findRewardingsByIdUser(user);
    }

    public List<Rewarding> findRewardingByReward(Reward reward) {
        return rewardingRepository.findRewardingsByIdReward(reward);
    }
}
