package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.exceptions.reward_service_exceptions.RewardServiceException;
import com.example.users_and_rewards.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/rewards")
public class RewardController {
    private static final String TITLE_ATTRIBUTE = "title";
    private static final String DESCRIPTION_ATTRIBUTE = "description";
    private static final String REWARD_ATTRIBUTE = "reward";
    private static final String REWARDINGS_ATTRIBUTE = "rewardings";
    private static final String REWARDS_ATTRIBUTE = "rewards";

    private static final String TITLE_PATH_VARIABLE = TITLE_ATTRIBUTE;

    private static final String REWARDS_PAGE_PATH = "rewards_tmpl/rewards";
    private static final String REWARD_PAGE_PATH = "rewards_tmpl/reward-page";
    private static final String EDIT_PAGE_PATH = "rewards_tmpl/edit-reward";
    private static final String REDIRECT_TO_REWARDS_PATH = "redirect:/rewards";

    private RewardService rewardService;

    @Autowired
    public void setRewardService(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping
    public String showAllRewards(Model model, @RequestParam(name = TITLE_ATTRIBUTE, required = false) String title,
                                 @RequestParam(name = DESCRIPTION_ATTRIBUTE, required = false) String description) {

        model.addAttribute(TITLE_ATTRIBUTE, title);
        model.addAttribute(DESCRIPTION_ATTRIBUTE, description);
        model.addAttribute(REWARDS_ATTRIBUTE, rewardService.getRewards(title, description));

        return REWARDS_PAGE_PATH;
    }

    @GetMapping("/show/{title}")
    public String showReward(Model model, @PathVariable(TITLE_PATH_VARIABLE) String title) {
        try {
            Reward reward = rewardService.getRewardByTitle(title);
            model.addAttribute(REWARD_ATTRIBUTE, reward);
            model.addAttribute(REWARDINGS_ATTRIBUTE, rewardService.findRewardingsByReward(reward));
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return REWARD_PAGE_PATH;
    }

    @GetMapping("/edit")
    public String addReward(Model model) {
        model.addAttribute(REWARD_ATTRIBUTE, new Reward());

        return EDIT_PAGE_PATH;
    }

    @PostMapping("/edit")
    public String addReward(@ModelAttribute(value = REWARD_ATTRIBUTE) Reward reward) {
        try {
            rewardService.edit(reward);
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return REDIRECT_TO_REWARDS_PATH;
    }

    @GetMapping("/edit/{title}")
    public String editReward(Model model, @PathVariable(name = TITLE_PATH_VARIABLE) String title) {
        try {
            model.addAttribute(REWARD_ATTRIBUTE, rewardService.getRewardByTitle(title));
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return EDIT_PAGE_PATH;
    }

    @GetMapping("/delete/{title}")
    public String deleteUser(@PathVariable(TITLE_PATH_VARIABLE) String title) {
        try {
            rewardService.delete(rewardService.getRewardByTitle(title));
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return REDIRECT_TO_REWARDS_PATH;
    }

}