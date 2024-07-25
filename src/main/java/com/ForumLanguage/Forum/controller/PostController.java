package com.ForumLanguage.Forum.controller;

import com.ForumLanguage.Forum.dto.PostDto;
import com.ForumLanguage.Forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/Post")
    public String postUp(Model model, Authentication authentication){
        boolean userIn = authentication != null && authentication.isAuthenticated() ;
        if(userIn == false){
            return "redirect:/Login";
        }
        PostDto postDto = new PostDto();
        model.addAttribute("newpost",postDto);
        model.addAttribute("userIn",userIn);
        return "post_up";
    }

    @PostMapping("/Post_Up")
    public String Post_Up(@ModelAttribute PostDto postDto, Authentication authentication){
        postService.savePost(postDto,authentication.getName());
        return "redirect:/Home";
    }


}
