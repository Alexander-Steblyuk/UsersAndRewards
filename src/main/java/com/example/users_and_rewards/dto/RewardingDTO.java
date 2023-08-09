package com.example.users_and_rewards.dto;

import java.time.LocalDate;

public class RewardingDTO {
    private Long userId;
    private String rewardTitle;
    private LocalDate rewardingDate;

    public RewardingDTO() {
    }

    public RewardingDTO(Long userId, String rewardTitle, LocalDate rewardingDate) {
        this.userId = userId;
        this.rewardTitle = rewardTitle;
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
