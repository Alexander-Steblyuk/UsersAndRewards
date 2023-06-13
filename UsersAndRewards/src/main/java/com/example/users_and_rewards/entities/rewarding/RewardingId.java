package com.example.users_and_rewards.entities.rewarding;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class RewardingId {
    @Column(name = "userid")
    private Long userId;

    @Column(name = "rewardtitle")
    private String rewardTitle;

    @Column(name = "rewarddate")
    private LocalDate rewardingDate;

    public RewardingId(User user, Reward reward, LocalDate rewardingDate) {
        this.userId = user.getId();
        this.rewardTitle = reward.getTitle();
        this.rewardingDate = rewardingDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRewardTitle() {
        return rewardTitle;
    }

    public void setRewardTitle(String rewardTitle) {
        this.rewardTitle = rewardTitle;
    }

    public LocalDate getRewardingDate() {
        return rewardingDate;
    }

    public void setRewardingDate(LocalDate rewardingDate) {
        this.rewardingDate = rewardingDate;
    }
}
