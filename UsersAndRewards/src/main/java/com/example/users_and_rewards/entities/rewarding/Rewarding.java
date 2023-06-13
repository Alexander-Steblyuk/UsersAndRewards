package com.example.users_and_rewards.entities.rewarding;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.rewarding.RewardingId;
import com.example.users_and_rewards.entities.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Rewardees")
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
}
