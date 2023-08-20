package com.example.users_and_rewards.entities.rewarding;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class RewardingId {
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(targetEntity = Reward.class)
    @JoinColumn(name = "rewardtitle")
    private Reward reward;

    @Column(name = "rewarddate")
    private LocalDate rewardingDate;

    public RewardingId() {

    }

    public RewardingId(User user, Reward reward, LocalDate rewardingDate) {
        this.user = user;
        this.reward = reward;
        this.rewardingDate = rewardingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RewardingId that = (RewardingId) o;
        return Objects.equals(user, that.user) && Objects.equals(reward, that.reward) &&
                Objects.equals(rewardingDate, that.rewardingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, reward, rewardingDate);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public LocalDate getRewardingDate() {
        return rewardingDate;
    }

    public void setRewardingDate(LocalDate rewardingDate) {
        this.rewardingDate = rewardingDate;
    }
}
