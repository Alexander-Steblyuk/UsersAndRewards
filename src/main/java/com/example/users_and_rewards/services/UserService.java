package com.example.users_and_rewards.services;

import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.exceptions.user_service_exceptions.*;
import com.example.users_and_rewards.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
        if (user.getId() != null) {
            Optional<User> oldUser = userRepository.findById(user.getId());
            update(user, oldUser.orElseThrow(UserNotFoundException::new));
        } else {
            save(user);
        }
    }
    private void save(User user) throws UserServiceException {
        if (user.getFirstName() == null || user.getFirstName().equals(EMPTY_STRING)) {
            throw new IllegalFirstNameException();
        } else if (user.getLastName() == null || user.getLastName().equals(EMPTY_STRING)) {
            throw new IllegalLastNameException();
        } else if (user.getBirthday() == null || !yearsCheck(user.getBirthday())) {
            throw new IllegalBirthDayException();
        } else if (userRepository.findUserByFirstNameAndLastNameAndBirthday(user.getFirstName(),
                user.getLastName(), user.getBirthday()) != null) {
            throw new UserAlreadyExistsException();
        } else {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                throw new UserServiceException(e.getMessage(), e);
            }
        }
    }

    private void update(User user, User oldUser) throws UserServiceException {
        try {
            if (!oldUser.getFirstName().equals(user.getFirstName())) {
                userRepository.updateFirstName(user.getId(), user.getFirstName());
            }
            if (!oldUser.getLastName().equals(user.getLastName())) {
                userRepository.updateLastName(user.getId(), user.getLastName());
            }
            if (!oldUser.getBirthday().equals(user.getBirthday())) {
                userRepository.updateBirthday(user.getId(), user.getBirthday());
            }
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    public void delete(User user) throws UserServiceException {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    public User getUserById(Long id) throws UserServiceException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAllUsers(String fullName, LocalDate birthday) {
        List<User> users;

        if (fullName == null && birthday == null) {
            users = userRepository.findAll();
        } else {
            if (fullName != null && birthday != null) {
                users = userRepository.findUsersByFullNameAndBirthday(fullName.toLowerCase(Locale.ROOT), birthday);
            } else if (fullName != null) {
                users = userRepository.findUsersByFullName(fullName.toLowerCase(Locale.ROOT));
            } else {
                users = userRepository.findUsersByBirthday(birthday);
            }
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
