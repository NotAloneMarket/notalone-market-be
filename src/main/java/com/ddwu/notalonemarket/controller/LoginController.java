package com.ddwu.notalonemarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/thymeleaf-login")
    public String loginPage() {
    	  System.out.println("✅ /login 컨트롤러 진입");
        return "login"; // templates/login.html
    }
}
