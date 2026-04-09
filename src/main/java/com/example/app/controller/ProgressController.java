package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.service.ProgressService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveProgress(
            @RequestParam String subject,
            @RequestParam int score,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            System.out.println("❌ No user in session. Progress NOT saved.");
            return "NO_SESSION";
        }

        progressService.recordScore(user.getId(), subject, score);

        System.out.println("✅ Progress saved for user " + user.getId()
                + " subject=" + subject + " score=" + score);

        return "OK";
    }
}
