package com.example.users_and_rewards.repositories;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, String> {
    /*@Modifying
    @Transactional
    @Query(value = "call reward_insert(:title, :description)", nativeQuery = true)
    void save(@Param("title") String title, @Param("description") String description);*/

    @Modifying
    @Transactional
    @Query(value = "call reward_title_update(:title, :new_title)", nativeQuery = true)
    void updateTitle(@Param("title") String title, @Param("new_title") String newTitle);

    @Modifying
    @Transactional
    @Query(value = "call reward_description_update(:title, :new_description)", nativeQuery = true)
    void updateDescription(@Param("title") String title, @Param("new_description") String newDescription);

    @Modifying
    @Transactional
    @Query(value = "call reward_delete(:title)", nativeQuery = true)
    void delete(@Param("title") String title);

    @Query(value = "select * from rewardings where rewardtitle = :title", nativeQuery = true)
    List<Rewarding> findAllRewardings(@Param("title") String title);

    Reward findRewardByTitle(String title);
}
