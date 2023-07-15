package com.example.users_and_rewards.entities.rewarding;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Rewardings")
public class Rewarding {
    @EmbeddedId
    private RewardingId id;

    public Rewarding() {
    }

    public Rewarding(User user, Reward reward, LocalDate rewardingDate) {
        id = new RewardingId(user, reward, rewardingDate);
    }

    public RewardingId getId() {
        return id;
    }

    public void setId(RewardingId id) {
        this.id = id;
    }

    public User getUser() {
        return id.getUser();
    }

    public void setUser(User user) {
        id.setUser(user);
    }

    public Reward getReward() {
        return id.getReward();
    }

    public void setReward(Reward reward) {
        id.setReward(reward);
    }

    public LocalDate getRewardingDate() {
        return id.getRewardingDate();
    }

    public void setRewardingDate(LocalDate rewardingDate) {
        id.setRewardingDate(rewardingDate);
    }

}
