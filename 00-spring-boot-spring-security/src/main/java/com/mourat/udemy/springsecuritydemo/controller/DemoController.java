package com.mourat.udemy.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/showLogin")
    public String showLogin(){
        return "login-form";
    }

    @GetMapping("/leaders")
    public String leadersPage(){
        return "leaders";
    }

    @GetMapping("/admins")
    public String adminsPage(){
        return "admins";
    }
}
