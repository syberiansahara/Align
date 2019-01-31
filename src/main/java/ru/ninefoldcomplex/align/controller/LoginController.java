package ru.ninefoldcomplex.align.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @RequestMapping("/doLogin")
    public boolean login(String login, String password) {
        return "admin".equals(login)
                & "admin".equals(password);
    }
}