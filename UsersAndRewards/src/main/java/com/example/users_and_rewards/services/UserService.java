package com.example.users_and_rewards.services;

import com.example.users_and_rewards.entities.User;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.exceptions.UserServiceException;
import com.example.users_and_rewards.repositories.RewardingRepository;
import com.example.users_and_rewards.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private static final String EMPTY_STRING = "";

    private static final String INVALID_FIRSTNAME_INSERT_MESSAGE = "FIRST NAME CAN`T BE NULL OR EMPTY!";
    private static final String INVALID_LASTNAME_INSERT_MESSAGE = "LAST NAME CAN`T BE NULL OR EMPTY!";
    private static final String INVALID_BIRTHDAY_INSERT_MESSAGE = "INVALID BIRTHDAY VALUE!";
    private static final String EXISTING_USER_INSERT_MESSAGE = "USER WITH SAME PARAMS HAS ALREADY EXISTS!";

    private UserRepository userRepository;
    private RewardingRepository rewardingRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setRewardingRepository(RewardingRepository rewardingRepository) {
        this.rewardingRepository = rewardingRepository;
    }

    @Autowired


    public void save(User user) throws UserServiceException {
        String message = EMPTY_STRING;
        boolean isException = true;

        if (user.getFirstName() == null || user.getFirstName().equals(EMPTY_STRING)) {
            message = INVALID_FIRSTNAME_INSERT_MESSAGE;
        } else if (user.getLastName() == null || user.getLastName().equals(EMPTY_STRING)) {
            message = INVALID_LASTNAME_INSERT_MESSAGE;
        } else if (user.getBirthday() == null || !yearsCheck(user.getBirthday())) {
            message = INVALID_BIRTHDAY_INSERT_MESSAGE;
        } else if (userRepository.findUserByFirstNameAndLastNameAndBirthday(user.getFirstName(),
                user.getLastName(), user.getBirthday()) != null) {
            message = EXISTING_USER_INSERT_MESSAGE;
        } else {
            isException = false;
        }

        if (isException) {
            throw new UserServiceException(message);
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    public void update(User user) throws UserServiceException {
        User oldUser = userRepository.findById(user.getId()).orElse(new User());

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

    public List<Rewarding> findRewardings(User user) {
        return rewardingRepository.findRewardingsByUser(user);
    }

    private boolean yearsCheck(LocalDate birthday) {
        return LocalDate.now().getYear() - birthday.getYear() < 120 &&
                birthday.getYear() <= LocalDate.now().getYear();
    }
}
