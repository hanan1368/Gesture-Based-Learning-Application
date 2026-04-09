package com.example.app.controller;

import com.example.app.model.Role;
import com.example.app.model.User;
import com.example.app.model.ProgressRecord;
import com.example.app.service.AdminService;
import com.example.app.service.ProgressService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class DashboardController {

    private final AdminService adminService;
    private final ProgressService progressService;

    public DashboardController(AdminService adminService,
                               ProgressService progressService){
        this.adminService = adminService;
        this.progressService = progressService;
    }

    // ================= STUDENT DASHBOARD =================
    @GetMapping("/dashboard")
    public String studentDashboard(HttpSession session, Model model){

        User user = (User) session.getAttribute("loggedInUser");

        if(user == null){
            return "redirect:/login?role=student";
        }

        if(user.getRole() != Role.STUDENT){
            if(user.getRole() == Role.PARENT){
                return "redirect:/parent/dashboard";
            }
            if(user.getRole() == Role.ADMIN){
                return "redirect:/admin/dashboard";
            }
        }

        model.addAttribute("username", user.getUsername());

        model.addAttribute("announcements",
                adminService.getAnnouncements());

        return "student/student-dashboard";
    }


    // ================= COURSES =================
    @GetMapping("/courses")
    public String studentCourses(HttpSession session){

        User user = (User) session.getAttribute("loggedInUser");

        if(user == null || user.getRole() != Role.STUDENT){
            return "redirect:/login?role=student";
        }

        return "student/student-courses";
    }


    // ================= REWARDS =================
    @GetMapping("/rewards")
    public String studentRewards(HttpSession session, Model model){

        User user = (User) session.getAttribute("loggedInUser");

        if(user == null || user.getRole() != Role.STUDENT){
            return "redirect:/login?role=student";
        }

        int studentId = user.getId();

        List<ProgressRecord> records =
                progressService.getProgressForUser(studentId);

        int totalPoints = 0;

        for(ProgressRecord r : records){
            totalPoints += r.getScore();   // score from progress table
        }

        model.addAttribute("points", totalPoints);

        return "student/student-rewards";
    }
}