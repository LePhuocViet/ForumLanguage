package com.ForumLanguage.Forum.controller;

import com.ForumLanguage.Forum.dto.AccountsDto;
import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.enity.Account;
import com.ForumLanguage.Forum.enity.User;
import com.ForumLanguage.Forum.exception.AccountException;
import com.ForumLanguage.Forum.service.AccountService;
import com.ForumLanguage.Forum.service.EmailSenderService;
import com.ForumLanguage.Forum.service.EmailService;
import com.ForumLanguage.Forum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class RegisterController {
    @Autowired
    AccountService accountService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    @GetMapping("/Register")
    public String Register(Model model){
        AccountsDto accountsDto = new AccountsDto();
        model.addAttribute("newAccount",accountsDto);
        return "register";
    }


    @PostMapping("/register-check")
    public String Authentication(@ModelAttribute("newAccount") AccountsDto accountsDto, HttpServletRequest httpServletRequest){
        accountService.checkPassword(accountsDto,httpServletRequest.getParameter("repassword"));
        accountService.CreateAccount(accountsDto,httpServletRequest.getParameter("email"),httpServletRequest.getParameter("code"));
        return "login";

    }

    @GetMapping("/Send")
    @ResponseBody
    public String send(HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getParameter("email");
        return emailService.checkEmail(email);
    }



}
