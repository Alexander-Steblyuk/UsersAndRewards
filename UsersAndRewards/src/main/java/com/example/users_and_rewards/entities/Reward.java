package com.example.users_and_rewards.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "reward")
public class Reward {
    @Id
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    public Reward() {
    }

    public Reward(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
