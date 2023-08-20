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
    private static final String TITLE_ATTR_NAME = "title";
    private static final String DESCRIPTION_ATTR_NAME = "description";
    private static final String REWARD_ATTR_NAME = "reward";
    private static final String REWARDINGS_ATTR_NAME = "rewardings";
    private static final String REWARDS_ATTR_NAME = "rewards";

    private static final String PATH_VAR_TITLE_NAME = TITLE_ATTR_NAME;

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
    public String showAllRewards(Model model, @RequestParam(name = TITLE_ATTR_NAME, required = false) String title,
                                 @RequestParam(name = DESCRIPTION_ATTR_NAME, required = false) String description) {

        model.addAttribute(TITLE_ATTR_NAME, title);
        model.addAttribute(DESCRIPTION_ATTR_NAME, description);
        model.addAttribute(REWARDS_ATTR_NAME, rewardService.getRewards(title, description));

        return REWARDS_PAGE_PATH;
    }

    @GetMapping("/show/{title}")
    public String showReward(Model model, @PathVariable(PATH_VAR_TITLE_NAME) String title) {
        try {
            Reward reward = rewardService.getRewardByTitle(title);
            model.addAttribute(REWARD_ATTR_NAME, reward);
            model.addAttribute(REWARDINGS_ATTR_NAME, rewardService.findRewardingsByReward(reward));
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return REWARD_PAGE_PATH;
    }

    @GetMapping("/edit")
    public String addReward(Model model) {
        model.addAttribute(REWARD_ATTR_NAME, new Reward());

        return EDIT_PAGE_PATH;
    }

    @PostMapping("/edit")
    public String addReward(@ModelAttribute(value = REWARD_ATTR_NAME) Reward reward) {
        try {
            rewardService.edit(reward);
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return REDIRECT_TO_REWARDS_PATH;
    }

    @GetMapping("/edit/{title}")
    public String editReward(Model model, @PathVariable(name = PATH_VAR_TITLE_NAME) String title) {
        try {
            model.addAttribute(REWARD_ATTR_NAME, rewardService.getRewardByTitle(title));
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return EDIT_PAGE_PATH;
    }

    @GetMapping("/delete/{title}")
    public String deleteUser(@PathVariable(PATH_VAR_TITLE_NAME) String title) {
        try {
            rewardService.delete(rewardService.getRewardByTitle(title));
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return REDIRECT_TO_REWARDS_PATH;
    }

}