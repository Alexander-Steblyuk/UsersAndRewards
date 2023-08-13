package com.example.users_and_rewards.services;

import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.exceptions.user_service_exceptions.*;
import com.example.users_and_rewards.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private static final String EMPTY_STRING = "";

    private UserRepository userRepository;
    private RewardingService rewardingService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRewardingService(RewardingService rewardingService) {
        this.rewardingService = rewardingService;
    }

    public void edit(User user) throws UserServiceException {
        if (user.getFirstName() == null || user.getFirstName().equals(EMPTY_STRING)) {
            throw new IllegalFirstNameException();
        }

        if (user.getLastName() == null || user.getLastName().equals(EMPTY_STRING)) {
            throw new IllegalLastNameException();
        }

        if (user.getBirthday() == null || !yearsCheck(user.getBirthday())) {
            throw new IllegalBirthDayException();
        }

        if (userRepository.findUserByFirstNameAndLastNameAndBirthday(user.getFirstName(),
                user.getLastName(), user.getBirthday()) != null) {
            throw new UserAlreadyExistsException();
        }

        if (user.getId() != null) {
            update(user);
        } else {
            save(user);
        }
    }

    private void save(User user) throws UserServiceException {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage(), e);
        }
    }

    private void update(User user) throws UserServiceException {
        try {
            userRepository.update(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthday());
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage(), e);
        }
    }

    public void delete(User user) throws UserServiceException {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage(), e);
        }
    }

    public User getUserById(Long id) throws UserServiceException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getUsers(String fullName, LocalDate birthday) {
        List<User> users;

        if (fullName == null && birthday == null) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findFilteredUsers(fullName, birthday);
        }

        return users;
    }

    public List<Rewarding> findRewardings(User user) {
        return rewardingService.findRewardingByUser(user);
    }

    private boolean yearsCheck(LocalDate birthday) {
        return LocalDate.now().getYear() - birthday.getYear() < 120 &&
                birthday.getYear() <= LocalDate.now().getYear();
    }
}
