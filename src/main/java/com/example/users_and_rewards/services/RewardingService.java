package com.example.users_and_rewards.services;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.entities.rewarding.RewardingId;
import com.example.users_and_rewards.exceptions.rewarding_service_exceptions.RewardingServiceException;
import com.example.users_and_rewards.repositories.RewardingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class RewardingService {
    private RewardingRepository rewardingRepository;

    @Autowired
    public void setRewardingRepository(RewardingRepository rewardingRepository) {
        this.rewardingRepository = rewardingRepository;
    }

    public void edit(Rewarding rewarding)  throws RewardingServiceException {
        try {
            rewardingRepository.save(rewarding.getUser().getId(),
                    rewarding.getReward().getTitle(), rewarding.getRewardingDate());
        } catch (Exception e) {
            throw new RewardingServiceException(e.getMessage());
        }
    }

    public void edit(User user, Reward reward, LocalDate rewardingDate)  throws RewardingServiceException {
        try {
            rewardingRepository.save(user.getId(), reward.getTitle(), rewardingDate);
        } catch (Exception e) {
            throw new RewardingServiceException(e.getMessage());
        }
    }

    public void update(User user, Reward reward, LocalDate date) throws RewardingServiceException {
        Rewarding oldRewarding = rewardingRepository.findRewardingByIdUserAndIdRewardAndIdRewardingDate(user,
                reward, date);

        try {
            rewardingRepository.update(oldRewarding.getUser().getId(), oldRewarding.getReward().getTitle(),
                    oldRewarding.getRewardingDate(), user.getId(), reward.getTitle(), date);
        } catch (Exception e) {
            throw new RewardingServiceException(e.getMessage());
        }
    }

    public void delete(Rewarding rewarding) throws RewardingServiceException {
        try {
            rewardingRepository.delete(rewarding.getUser().getId(),
                    rewarding.getReward().getTitle(), rewarding.getRewardingDate());
        } catch (Exception e) {
            throw new RewardingServiceException(e.getMessage());
        }
    }

    public void delete(User user, Reward reward, LocalDate rewardingDate) throws RewardingServiceException {
        try {
            rewardingRepository.delete(user.getId(), reward.getTitle(), rewardingDate);
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

    public List<Rewarding> getRewardings(String fullName, String title, LocalDate rewardingDate) {
        if (fullName == null && title == null && rewardingDate == null) {
            return rewardingRepository.findAll();
        }

        return rewardingRepository.findFilteredRewardings(fullName, title, rewardingDate);
    }

    public Rewarding findById(RewardingId id) {
        return rewardingRepository.findById(id).orElseThrow();
    }

    public Rewarding findById(User user, Reward reward, LocalDate rewardingDate) {
        return rewardingRepository.findRewardingByIdUserAndIdRewardAndIdRewardingDate(user, reward, rewardingDate);
    }
}
