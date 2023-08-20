package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.dto.RewardingDTO;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.exceptions.reward_service_exceptions.RewardServiceException;
import com.example.users_and_rewards.exceptions.rewarding_service_exceptions.RewardingServiceException;
import com.example.users_and_rewards.services.RewardService;
import com.example.users_and_rewards.services.RewardingService;
import com.example.users_and_rewards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Controller
@RequestMapping("/rewardings")
public class RewardingController {
    private static final String FULL_NAME_ATTRIBUTE = "fullName";
    private static final String REWARD_TITLE_ATTRIBUTE = "title";
    private static final String REWARDING_DATE_ATTRIBUTE = "rewardingDate";
    private static final String REWARDINGS_ATTRIBUTE = "rewardings";
    private static final String REWARDS_ATTRIBUTE = "rewards";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String REWARDING_ATTRIBUTE = "rewarding";

    private static final String ID_PATH_VARIABLE = "id";

    private static final String REWARDINGS_PAGE_PATH = "rewardings_tmpl/rewardings";
    private static final String EDIT_REWARDING_PAGE_PATH = "rewardings_tmpl/edit_rewarding";
    private static final String REDIRECT_TO_REWARDINGS_PAGE_PATH = "redirect:/rewardings";

    private static final int INDEX_OF_FIRST_ID_PARAM = 0;
    private static final int INDEX_OF_SECOND_ID_PARAM = 1;
    private static final int INDEX_OF_THIRD_ID_PARAM = 2;

    private static final String ID_PATH_VARIABLE_SPLITERATOR = "_";

    private RewardingService rewardingService;
    private UserService userService;
    private RewardService rewardService;

    @Autowired
    public void setRewardingService(RewardingService rewardingService) {
        this.rewardingService = rewardingService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRewardService(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping
    public String showAllRewardings(Model model,
                            @RequestParam(name = FULL_NAME_ATTRIBUTE, required = false) String fullName,
                            @RequestParam(name = REWARD_TITLE_ATTRIBUTE, required = false) String title,
                            @RequestParam(name = REWARDING_DATE_ATTRIBUTE, required = false) LocalDate rewardingDate) {

        model.addAttribute(FULL_NAME_ATTRIBUTE, fullName);
        model.addAttribute(REWARD_TITLE_ATTRIBUTE, title);
        model.addAttribute(REWARDING_DATE_ATTRIBUTE, rewardingDate);
        model.addAttribute(REWARDINGS_ATTRIBUTE, rewardingService.getRewardings(fullName, title, rewardingDate));

        return REWARDINGS_PAGE_PATH;
    }

    @GetMapping("/edit")
    public String addRewarding(Model model) {
        model.addAttribute(REWARDING_ATTRIBUTE, new RewardingDTO());
        model.addAttribute(USERS_ATTRIBUTE, userService.getUsers(null, null));
        model.addAttribute(REWARDS_ATTRIBUTE, rewardService.getRewards(null, null));

        return EDIT_REWARDING_PAGE_PATH;
    }

    @PostMapping("/edit")
    public String addReward(@ModelAttribute(value = REWARDING_ATTRIBUTE) RewardingDTO rewardingDTO) {
        Rewarding rewarding = new Rewarding();

        try {
            rewarding.setUser(userService.getUserById(rewardingDTO.getUserId()));
            rewarding.setReward(rewardService.getRewardByTitle(rewardingDTO.getRewardTitle()));
            rewarding.setRewardingDate(rewardingDTO.getRewardingDate());

            rewardingService.edit(rewarding);
        } catch (RewardServiceException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return REDIRECT_TO_REWARDINGS_PAGE_PATH;
    }

    @GetMapping("/edit/{id}")
    public String editReward(Model model, @PathVariable(name = ID_PATH_VARIABLE) String id) {
        String[] params = id.split(ID_PATH_VARIABLE_SPLITERATOR);


        model.addAttribute(USERS_ATTRIBUTE, userService.getUsers(null, null));
        model.addAttribute(REWARDS_ATTRIBUTE, rewardService.getRewards(null, null));

        model.addAttribute(REWARDING_ATTRIBUTE, new RewardingDTO(Long.parseLong(params[INDEX_OF_FIRST_ID_PARAM]),
                params[INDEX_OF_SECOND_ID_PARAM], LocalDate.parse(params[INDEX_OF_THIRD_ID_PARAM])));

        try {
            rewardingService.delete(userService.getUserById(Long.parseLong(params[INDEX_OF_FIRST_ID_PARAM])),
                rewardService.getRewardByTitle(params[INDEX_OF_SECOND_ID_PARAM]),
                    LocalDate.parse(params[INDEX_OF_THIRD_ID_PARAM]));
        } catch (RewardingServiceException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return EDIT_REWARDING_PAGE_PATH;
    }

    @GetMapping("/delete/{id}")
    public String deleteRewarding(@PathVariable(ID_PATH_VARIABLE) String id) {
        String[] params = id.split(ID_PATH_VARIABLE_SPLITERATOR);

        try {
            rewardingService.delete(userService.getUserById(Long.parseLong(params[INDEX_OF_FIRST_ID_PARAM])),
                rewardService.getRewardByTitle(params[INDEX_OF_SECOND_ID_PARAM]),
                    LocalDate.parse(params[INDEX_OF_THIRD_ID_PARAM]));
        } catch (RewardingServiceException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return REDIRECT_TO_REWARDINGS_PAGE_PATH;
    }

}