package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.exceptions.reward_service_exceptions.RewardServiceException;
import com.example.users_and_rewards.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/rewards")
public class RewardController {
    private RewardService rewardService;

    @Autowired
    public void setRewardService(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping
    public String showAllRewards(Model model, @RequestParam(name = "title", required = false) String title,
                        @RequestParam(name = "description", required = false) String description) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("rewards", rewardService.getRewards(title, description));

        return "rewards_tmpl/rewards";
    }

    @GetMapping("/show/{title}")
    public String showReward(Model model, @PathVariable("title") String title) {
        try {
            Reward reward = rewardService.getRewardByTitle(title);
            model.addAttribute("reward", reward);
            model.addAttribute("rewardings", rewardService.findRewardingsByReward(reward));
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return "rewards_tmpl/reward-page";
    }

    @GetMapping("/edit")
    public String addReward(Model model) {
        model.addAttribute("reward", new Reward());

        return "rewards_tmpl/edit-reward";
    }

    @PostMapping("/edit")
    public String addReward(@ModelAttribute(value = "reward") Reward reward) {
        try {
            rewardService.edit(reward);
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return "redirect:/rewards";
    }

    @GetMapping("/edit/{title}")
    public String editReward(Model model, @PathVariable(name = "title") String title) {
        try {
            model.addAttribute("reward", rewardService.getRewardByTitle(title));
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return "rewards_tmpl/edit-reward";
    }

    @GetMapping("/delete/{title}")
    public String deleteUser(@PathVariable("title") String title) {
        try {
            rewardService.delete(rewardService.getRewardByTitle(title));
        } catch (RewardServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return "redirect:/rewards";
    }

}