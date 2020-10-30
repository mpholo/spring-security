package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String login() {
        System.out.println("login page");
        return "login";
    }

    @GetMapping("courses")
    public String getCourses() {
        System.out.println("course controller ");
        return "courses";
    }
}
