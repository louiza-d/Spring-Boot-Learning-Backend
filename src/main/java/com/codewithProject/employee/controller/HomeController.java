package com.codewithProject.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Welcome to our Employee Management System ");
        model.addAttribute("message", "Empower your team and streamline workforce management with our intuitive Employee Management System");
        return "index";
    }
}
