package com.ddwu.notalonemarket.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Welcome to Notalone Market!";
    }
}
