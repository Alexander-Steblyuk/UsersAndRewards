package com.example.users_and_rewards.repositories;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.entities.rewarding.RewardingId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RewardingRepository extends JpaRepository<Rewarding, RewardingId> {
    @Modifying
    @Transactional
    @Query(value = "call rewarding_insert(:user_id, :reward_title, :reward_date)", nativeQuery = true)
    void save(@Param("user_id") Long userId, @Param("reward_title") String rewardTitle,
              @Param("reward_date") LocalDate date);

    @Modifying
    @Transactional
    @Query(value = "call rewarding_update(:user_id, :reward_title, :reward_date, :new_user_id, :new_reward_title," +
            ":new_reward_date)", nativeQuery = true)
    void update(@Param("user_id") Long userId, @Param("reward_title") String rewardTitle,
                     @Param("reward_date") LocalDate date, @Param("new_user_id") Long newUserId,
                    @Param("new_reward_title") String newRewardTitle, @Param("new_reward_date") LocalDate newRewardDate);

    /*@Modifying
    @Transactional
    @Query(value = "call rewarding_reward_update(:user_id, :reward_title, :reward_date, :new_reward_title)", nativeQuery = true)
    void updateReward(@Param("user_id") Long userId, @Param("reward_title") String rewardTitle,
                     @Param("reward_date") LocalDate date, @Param("new_reward_title") String newRewardTitle);

    @Modifying
    @Transactional
    @Query(value = "call rewarding_date_update(:user_id, :reward_title, :reward_date, :new_reward_date)", nativeQuery = true)
    void updateDate(@Param("user_id") Long userId, @Param("reward_title") String rewardTitle,
                     @Param("reward_date") LocalDate date, @Param("new_reward_date") LocalDate newRewardDate);*/

    @Modifying
    @Transactional
    @Query(value = "call rewarding_delete(:user_id, :reward_title, :reward_date)", nativeQuery = true)
    void delete(@Param("user_id") Long userId, @Param("reward_title") String rewardTitle,
              @Param("reward_date") LocalDate date);

    Rewarding findRewardingByIdUserAndIdRewardAndIdRewardingDate(User user, Reward reward, LocalDate date);

    List<Rewarding> findRewardingsByIdUser(User user);

    List<Rewarding> findRewardingsByIdReward(Reward reward);

    @Query(value = "select * from rewardings where " +
            "UserId IN (select id from users where (:fullname is null OR lower(concat(firstName, ' ', lastName)) like " +
            "CONCAT('%', LOWER(:fullname), '%'))) AND RewardTitle IN (select title from rewards where (:title is null OR " +
            "lower(title) like CONCAT('%', LOWER(:title), '%'))) AND (cast(:rewardingDate as timestamp) is null OR " +
            "rewardDate >= :rewardingDate)", nativeQuery = true)
    List<Rewarding> findFilteredRewardings(@Param("fullname") String fullname, @Param("title") String title,
                                        @Param("rewardingDate") LocalDate rewardingDate);
}
