package com.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cinema.entity.User;
import com.cinema.repository.UserRepository;

@Controller
public class AuthController {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/") public String index() { return "index"; }
    @GetMapping("/login") public String login() { return "login"; }
    @GetMapping("/register") public String register() { return "register"; }

    @PostMapping("/register")
    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.UserRole.CUSTOMER);
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:/login?success";
    }
}