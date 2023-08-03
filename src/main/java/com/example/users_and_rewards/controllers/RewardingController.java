package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.entities.rewarding.Rewarding;
import com.example.users_and_rewards.entities.rewarding.RewardingId;
import com.example.users_and_rewards.exceptions.reward_service_exceptions.RewardServiceException;
import com.example.users_and_rewards.services.RewardService;
import com.example.users_and_rewards.services.RewardingService;
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

    @Autowired
    public void setRewardingService(RewardingService rewardingService) {
        this.rewardingService = rewardingService;
    }

    @GetMapping
    public String showAllRewardingss(Model model, @RequestParam(name = "fullName", required = false) String fullName,
                                     @RequestParam(name = "title", required = false) String title,
                                     @RequestParam(name = "rewardingDate", required = false)LocalDate rewardingDate) {

        /*model.addAttribute("fullName", fullName);
        model.addAttribute("title", title);
        model.addAttribute("rewardingDate", rewardingService.f);*/
        model.addAttribute("rewardings", rewardingService.getAll());

        return "rewardings_tmpl/rewardings";
    }

    @GetMapping("/edit")
    public String addRewarding(Model model) {
        model.addAttribute("rewarding", new Rewarding());

        return "rewardings_tmpl/edit-rewarding";
    }

    @PostMapping("/edit")
    public String addReward(@ModelAttribute(value = "rewarding") Rewarding rewarding) {
        rewardingService.edit(rewarding);

        return "redirect:/rewardings";
    }

    @GetMapping("/edit/{id}")
    public String editReward(Model model, @PathVariable(name = "id") RewardingId id) {
        model.addAttribute("reward", rewardingService.);

        return "rewardings_tmpl/edit-reward";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") RewardingId id) {
        rewardingService.delete(rewardingService.);

        return "redirect:/rewards";
    }

}