package com.ddwu.notalonemarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OnboardingController {

    @GetMapping("/onboarding")
    public String onboardingPage() {
        return "onboarding";
    }
}
