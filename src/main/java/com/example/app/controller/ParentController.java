package com.example.app.controller;

import com.example.app.model.ProgressRecord;
import com.example.app.model.User;
import com.example.app.service.ParentStudentService;
import com.example.app.service.ProgressService;
import com.example.app.service.AdminService;
import com.example.app.service.RewardService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/parent")
public class ParentController {

    private final ProgressService progressService;
    private final ParentStudentService parentStudentService;
    private final AdminService adminService;
    private final RewardService rewardService;

    public ParentController(
            ProgressService progressService,
            ParentStudentService parentStudentService,
            AdminService adminService,
            RewardService rewardService
    ) {
        this.progressService = progressService;
        this.parentStudentService = parentStudentService;
        this.adminService = adminService;
        this.rewardService = rewardService;
    }

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String parentDashboard(HttpSession session, Model model) {

        User parent = (User) session.getAttribute("loggedInUser");

        if (parent == null) {
            return "redirect:/login?role=parent";
        }

        model.addAttribute("username", parent.getUsername());
        model.addAttribute("announcements", adminService.getAnnouncements());

        return "parent/parent-dashboard";
    }

    // ================= PROGRESS PAGE =================
    @GetMapping("/progress")
    public String parentProgressPage(HttpSession session) {

        User parent = (User) session.getAttribute("loggedInUser");

        if (parent == null) {
            return "redirect:/login?role=parent";
        }

        return "parent/parent-child-progress";
    }

    // ================= PROGRESS DATA =================
    @GetMapping("/progress/data")
    @ResponseBody
    public Map<String, Integer> parentProgressData(HttpSession session) {

        Map<String, Integer> result = new HashMap<>();

        User parent = (User) session.getAttribute("loggedInUser");
        if (parent == null) return result;

        Integer studentId =
                parentStudentService.getStudentIdForParent(parent.getId());

        if (studentId == null) return result;

        List<ProgressRecord> records =
                progressService.getProgressForUser(studentId);

        for (ProgressRecord r : records) {
            result.put(r.getSubject(), r.getScore());
        }

        return result;
    }

    // ================= REWARDS PAGE =================
    @GetMapping("/rewards")
    public String parentRewards(HttpSession session, Model model) {

        User parent = (User) session.getAttribute("loggedInUser");

        if (parent == null) {
            return "redirect:/login?role=parent";
        }

        Integer studentId =
                parentStudentService.getStudentIdForParent(parent.getId());

        if (studentId == null) {
            return "redirect:/parent/dashboard";
        }

        Map<String, Integer> rewards =
                rewardService.calculateRewards(studentId);

        model.addAttribute("points", rewards.get("totalPoints"));
        model.addAttribute("level1", rewards.get("level1"));
        model.addAttribute("level2", rewards.get("level2"));
        model.addAttribute("level3", rewards.get("level3"));

        return "parent/parent-child-reward";
    }

    // ================= FEEDBACK PAGE =================
    @GetMapping("/feedback")
    public String parentFeedback(HttpSession session) {

        User parent = (User) session.getAttribute("loggedInUser");

        if (parent == null) {
            return "redirect:/login?role=parent";
        }

        return "parent/parent-feedback";
    }

    // ================= SUBMIT FEEDBACK =================
    @PostMapping("/feedback/submit")
    public String submitFeedback(
            @RequestParam String feedback,
            HttpSession session) {

        User parent = (User) session.getAttribute("loggedInUser");

        if (parent == null) {
            return "redirect:/login?role=parent";
        }

        adminService.saveParentFeedback(
                parent.getId(),
                parent.getUsername(),
                feedback
        );

        return "redirect:/parent/dashboard";
    }
}