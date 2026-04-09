package com.example.app.controller;

import com.example.app.service.AdminService;
import com.example.app.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String adminDashboard(){
        return "admin/admin-dashboard";
    }

    // ================= EDIT USERS =================
 @GetMapping("/edit-lesson")
public String editUsersPage(Model model){

    model.addAttribute("users",
            adminService.getAllUsers());

    return "admin/admin-edit-lesson";
}
    // Update user
    @PostMapping("/update-user")
    public String updateUser(@RequestParam int id,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String role){

        adminService.updateUser(id,username,password,role);

        return "redirect:/admin/edit-lesson";
    }


    // ================= DELETE USERS =================
    @GetMapping("/manage-users")
    public String manageUsersPage(Model model){

        model.addAttribute("users",
                adminService.getAllUsers());

        return "admin/admin-manage-users";
    }

    // Delete user
    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam int id){

        adminService.deleteUser(id);

        return "redirect:/admin/manage-users";
    }


    // ================= ANNOUNCEMENTS =================
    @GetMapping("/analytics")
    public String announcementPage(Model model){

        model.addAttribute("announcements",
                adminService.getAnnouncements());

        return "admin/admin-announcement";
    }

    @PostMapping("/post-announcement")
    public String postAnnouncement(@RequestParam String title,
                                   @RequestParam String message){

        adminService.postAnnouncement(title, message);

        return "redirect:/admin/analytics";
    }
    @GetMapping("/feedback")
public String viewParentFeedback(Model model){

    model.addAttribute("feedbackList",
            adminService.getAllFeedback());

    return "admin/admin-parent-feedback";
}
}