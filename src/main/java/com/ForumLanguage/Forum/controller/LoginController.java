package com.ForumLanguage.Forum.controller;

import com.ForumLanguage.Forum.exception.AccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/Login")
    public String Login(){

        return "login";
    }


}
