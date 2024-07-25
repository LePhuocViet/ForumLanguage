package com.ForumLanguage.Forum.controller;

import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingController {

    @Autowired
    UserService userService;

    @GetMapping("/Setting")
    public String setting(Model model, Authentication authentication){
        boolean userIn = authentication != null && authentication.isAuthenticated() ;
        if(userIn == false){
            return "redirect:/Login";
        }
        UsersDto usersDto = userService.FindUser(authentication.getName());
        model.addAttribute("usersDto",usersDto);
        model.addAttribute("userIn",userIn);
        if(userIn != false) {
            UsersDto usersDtos = userService.FindUserDtos(authentication.getName());
            model.addAttribute("UserDto" ,usersDtos);
        }
        return "setting";
    }

    @PostMapping("/save_setting")
    public String saveSetting(@ModelAttribute UsersDto usersDto,Authentication authentication){
        userService.save(usersDto,authentication.getName());
        return "redirect:/Setting";
    }

    @GetMapping("/delete")
    public String deleteAccount(Authentication authentication,HttpServletRequest httpServletRequest){
        userService.delete(authentication.getName());
        httpServletRequest.getSession().invalidate();
        return "redirect:/Login";
    }


}
