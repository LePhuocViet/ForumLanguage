package com.ForumLanguage.Forum.controller;

import com.ForumLanguage.Forum.dto.CommentsDto;
import com.ForumLanguage.Forum.enity.Post;
import com.ForumLanguage.Forum.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    PostService postService;
    @GetMapping("/Post")
    public String adminPage(Model model){
        List<Post> list = postService.fillAllPost();
        model.addAttribute("post",list);
        return "adminpost";
    }
    @PostMapping("/aDeleted")
    @ResponseBody
    public String aDeletedModel (Model model, HttpServletRequest httpServletRequest){
        postService.deletedPostByPostName(httpServletRequest.getParameter("ap"));
        List<Post> list = postService.fillAllPost();
        model.addAttribute("post",list);
        return "";
    }
}
