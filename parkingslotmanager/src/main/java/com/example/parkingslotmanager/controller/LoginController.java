package com.example.parkingslotmanager.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final String ADMIN_USER = "admin";
    private final String ADMIN_PASS = "admin123";

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            session.setAttribute("user", "admin");
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
