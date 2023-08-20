package com.example.users_and_rewards.repositories;

import com.example.users_and_rewards.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*@Modifying
    @Transactional
    @Query(value = "call user_insert(:firstName, :lastName, :birthday)", nativeQuery = true)
    void save(@Param("firstName") String firstName, @Param("lastName") String lastName,
              @Param("birthday") LocalDate birthday);*/

    @Modifying
    @Transactional
    @Query(value = "call user_update(:id, :new_first_name, :new_last_name, :new_birthday)", nativeQuery = true)
    void update(@Param("id") Long id, @Param("new_first_name") String newFirstName,
                         @Param("new_last_name") String newLastName, @Param("new_birthday") LocalDate newBirthday);

    /*@Modifying
    @Transactional
    @Query(value = "call user_last_name_update(:id, :new_last_name)", nativeQuery = true)
    void updateLastName(@Param("id") Long id, @Param("new_last_name") String newLastName);

    @Modifying
    @Transactional
    @Query(value = "call user_birthday_update(:id, :new_birthday)", nativeQuery = true)
    void updateBirthday(@Param("id") Long id, @Param("new_birthday") LocalDate newBirthday);*/

    @Modifying
    @Transactional
    @Query(value = "call user_delete(:id)", nativeQuery = true)
    void delete(@Param("id") Long id);

    @Query(value = "select * from users where birthday >= :birthday", nativeQuery = true)
    List<User> findUsersByBirthday(@Param("birthday") LocalDate birthday);

    @Query(value = "select * from users where (:fullname is null OR lower(concat(firstName, ' ', lastName)) like " +
            "CONCAT('%', LOWER(:fullname), '%')) AND (cast(:birthday as timestamp) is null OR birthday >= :birthday)",
            nativeQuery = true)
    List<User> findFilteredUsers(@Param("fullname") String fullName, @Param("birthday") LocalDate birthday);

    /*@Query(value = "select * from users where (concat(firstName, ' ', lastName)) like CONCAT('%', :fullname, '%') " +
            "and birthday >= :birthday", nativeQuery = true)
    List<User> findUsersByFullNameAndBirthday(@Param("fullname") String fullName, @Param("birthday")
    LocalDate birthday);*/

    User findUserByFirstNameAndLastNameAndBirthday(String firstname, String lastname, LocalDate birthday);

    /*@Query(value = "select * from rewardings where userid = :id", nativeQuery = true)
    List<Rewarding> findAllRewardings(@Param("id") Long id);*/
}
