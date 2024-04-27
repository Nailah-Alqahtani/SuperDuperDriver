package com.udacity.jwdnd.course1.cloudstorage.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class LoginController {
    @GetMapping("/login")
    public String viewLogin() {
        return "login";
    }
}
