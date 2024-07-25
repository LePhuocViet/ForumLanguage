package com.ForumLanguage.Forum.controller;

import com.ForumLanguage.Forum.dto.PostDetailDto;
import com.ForumLanguage.Forum.dto.PostDto;
import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.service.PostService;
import com.ForumLanguage.Forum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserHomeController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @GetMapping("/User")
    public String userHome(Model model, Authentication authentication, HttpServletRequest httpServletRequest){
        boolean userIn = authentication != null && authentication.isAuthenticated() ;
        UsersDto usersDto = userService.findUserByUsernumber(Integer.parseInt(httpServletRequest.getParameter("u")));
        List<PostDto> list = postService.findPostByusernumber(Integer.parseInt(httpServletRequest.getParameter("u")));
        List<PostDetailDto> postDetailDtosList = postService.findPostDetail();
        if(userIn == true){
            UsersDto usersChange = userService.findUserByUsername(authentication.getName());
            model.addAttribute("unumber",usersChange.getUsernumber());
            UsersDto usersDtos = userService.FindUserDtos(authentication.getName());
            model.addAttribute("UserDto" ,usersDtos);
            List<String> postlike = postService.findPostLike(authentication.getName());
            model.addAttribute("postlike",postlike);
        }else{
            List<String> postlike = new ArrayList<>();
            postlike.add(1+"");
            model.addAttribute("postlike",postlike);
        }

        model.addAttribute("postsDetailList",postDetailDtosList);
        model.addAttribute("PostUser",list);
        model.addAttribute("Useru",usersDto);
        model.addAttribute("userIn",userIn);
        return "user_home";
    }

    @PostMapping("/save-user")
    public String saveUser(Authentication authentication, HttpServletRequest httpServletRequest){
        userService.saveUser(authentication.getName(),httpServletRequest.getParameter("un"));
        String ub = httpServletRequest.getParameter("ub");
        return "redirect:/User?u=" +ub;
    }

}
