package com.mourat.udemy.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
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
}
