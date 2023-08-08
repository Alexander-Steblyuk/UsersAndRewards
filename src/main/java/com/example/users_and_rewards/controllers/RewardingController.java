package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.entities.rewarding.RewardingId;
import com.example.users_and_rewards.exceptions.reward_service_exceptions.RewardServiceException;
import com.example.users_and_rewards.services.RewardService;
import com.example.users_and_rewards.services.RewardingService;
import com.example.users_and_rewards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Controller
@RequestMapping("/rewardings")
public class RewardingController {
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
    public String showAllRewardings(Model model, @RequestParam(name = "fullName", required = false) String fullName,
                                     @RequestParam(name = "title", required = false) String title,
                                     @RequestParam(name = "rewardingDate", required = false) LocalDate rewardingDate) {

        model.addAttribute("fullName", fullName);
        model.addAttribute("title", title);
        model.addAttribute("rewardingDate", rewardingDate);
        model.addAttribute("rewardings", rewardingService.getRewardings(fullName, title, rewardingDate));

        return "rewardings_tmpl/rewardings";
    }

    @GetMapping("/edit")
    public String addRewarding(Model model) {
        model.addAttribute("rewardingId", new RewardingId());
        model.addAttribute("users", userService.getUsers(null, null));
        model.addAttribute("rewards", rewardService.getRewards(null, null));

        return "rewardings_tmpl/edit-rewarding";
    }

    @PostMapping("/edit")
    public String addReward(@ModelAttribute(value = "rewardingId") RewardingId rewardingId) {
        rewardingService.edit(new Rewarding(rewardingId));

        return "redirect:/rewardings";
    }

    @GetMapping("/edit/{id}")
    public String editReward(Model model, @PathVariable(name = "id") RewardingId id) {
        model.addAttribute("rewarding", rewardingService.findById(id));

        return "rewardings_tmpl/edit-rewarding";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") RewardingId id) {
        rewardingService.delete(rewardingService.findById(id));

        return "redirect:/rewards";
    }

}