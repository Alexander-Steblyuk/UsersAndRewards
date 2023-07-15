package com.example.users_and_rewards.controllers;

import com.example.users_and_rewards.entities.Reward;
import com.example.users_and_rewards.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("reward", rewardService.getRewardByTitle(title));

        return "rewards_tmpl/reward-page";
    }

    @GetMapping("/edit")
    public String addReward(Model model) {
        model.addAttribute("reward", new Reward());

        return "rewards_tmpl/edit-reward";
    }

    @GetMapping("/edit/{title}")
    public String editReward(Model model, @PathVariable(name = "title") String title) {
        model.addAttribute("reward", rewardService.getRewardByTitle(title));

        return "rewards_tmpl/edit-reward";
    }

    @GetMapping("/delete/{title}")
    public String deleteUser(@PathVariable("title") String title) {
        rewardService.delete(rewardService.getRewardByTitle(title));

        return "redirect:/rewards";
    }

    @PostMapping("/edit")
    public String addReward(@ModelAttribute(value = "reward") Reward reward) {
        rewardService.edit(reward);

        return "redirect:/rewards";
    }
}