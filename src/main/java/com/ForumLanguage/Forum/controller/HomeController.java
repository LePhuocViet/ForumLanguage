package com.ForumLanguage.Forum.controller;

import com.ForumLanguage.Forum.dto.CommentsDto;
import com.ForumLanguage.Forum.dto.PostDetailDto;
import com.ForumLanguage.Forum.dto.PostDto;
import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.enity.Post;
import com.ForumLanguage.Forum.service.AccountService;
import com.ForumLanguage.Forum.service.PostService;
import com.ForumLanguage.Forum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;


    @GetMapping("/Home")
    public String PageHome(Model model, Authentication authentication){
    boolean userIn = authentication != null && authentication.isAuthenticated();
    boolean userAdmin = false;

    if(userIn != false) {
        UsersDto usersDto = userService.FindUserDtos(authentication.getName());
        model.addAttribute("UserDto" ,usersDto);
        List<String> postlike = postService.findPostLike(authentication.getName());
        model.addAttribute("postlike",postlike);
        List<String> listAuthentication = new ArrayList<>();
        listAuthentication.addAll(
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
        userAdmin = accountService.checkAuthentication(listAuthentication);
        model.addAttribute("userAdmin",userAdmin);
    } else{
        List<String> postlike = new ArrayList<>();
        postlike.add(1+"");
        model.addAttribute("postlike",postlike);

    }
    List<PostDto> postDtoList = postService.findAllPost();
    List<PostDetailDto> postDetailDtosList = postService.findPostDetail();
    PostDto postDto = new PostDto();

    model.addAttribute("postsDetailList",postDetailDtosList);
    model.addAttribute("postsDto",postDto);
    model.addAttribute("postList",postDtoList);
    model.addAttribute("userIn",userIn);
        return "home";
    }

    @GetMapping("/")
    public String Home(){
        return "redirect:/Home";
    }

    @PostMapping("/like-post")
    @ResponseBody
    public String likepost(HttpServletRequest httpServletRequest, Authentication authentication, Model model, RedirectView redirectView){
        boolean userIn = authentication != null && authentication.isAuthenticated();
    if (!userIn){
        return "Please Login";
    }
        String postname = httpServletRequest.getParameter("lp");
        String username = authentication.getName();
        if(!postService.checkLike(postname,username)){
            postService.savePostLike(postname, username);
            return "Post liked successfully";
        } else {
            postService.deletedPostLike(postname,username);
            return "Post Unliked successfully";
        }


    }
    @GetMapping("/Search")
    public String Search(HttpServletRequest httpServletRequest,Authentication authentication,Model model){
        boolean userIn = authentication != null && authentication.isAuthenticated() ;

        List<PostDto> postDtoList = postService.Search(httpServletRequest.getParameter("title"));
        List<PostDetailDto> postDetailDtosList = postService.findPostDetail();
        if(userIn != false) {
            UsersDto usersDto = userService.FindUserDtos(authentication.getName());
            model.addAttribute("UserDto" ,usersDto);
        }
        PostDto postDto = new PostDto();
        model.addAttribute("postsDetailList",postDetailDtosList);
        model.addAttribute("postsDto",postDto);
        model.addAttribute("postList",postDtoList);
        model.addAttribute("userIn",userIn);
        return "home";
    }

    @GetMapping("/Kind")
    public String kind(HttpServletRequest httpServletRequest,Authentication authentication,Model model){
        List<PostDto> postDtoList = postService.findPostByLanguage(httpServletRequest.getParameter("language"));
        boolean userIn = authentication != null && authentication.isAuthenticated() ;
        if(userIn != false) {
            UsersDto usersDto = userService.FindUserDtos(authentication.getName());
            model.addAttribute("UserDto" ,usersDto);
        }
        List<PostDetailDto> postDetailDtosList = postService.findPostDetail();
        PostDto postDto = new PostDto();
        model.addAttribute("languageCss",httpServletRequest.getParameter("language"));
        model.addAttribute("postsDetailList",postDetailDtosList);
        model.addAttribute("postsDto",postDto);
        model.addAttribute("postList",postDtoList);
        model.addAttribute("userIn",userIn);
        return "home";
    }

    @GetMapping("/Sort")
    public String sort(HttpServletRequest httpServletRequest,Authentication authentication, Model model){
        List<PostDto> postDtoList = postService.findPostSort(httpServletRequest.getParameter("find"));
        boolean userIn = authentication != null && authentication.isAuthenticated() ;
        boolean userAdmin = false;
        if(userIn != false) {
            UsersDto usersDto = userService.FindUserDtos(authentication.getName());
            model.addAttribute("UserDto" ,usersDto);
            List<String> postlike = postService.findPostLike(authentication.getName());
            model.addAttribute("postlike",postlike);
            List<String> listAuthentication = new ArrayList<>();
            listAuthentication.addAll(
                    authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList())
            );
            userAdmin = accountService.checkAuthentication(listAuthentication);
            model.addAttribute("userAdmin",userAdmin);
        } else{
            List<String> postlike = new ArrayList<>();
            postlike.add(1+"");
            model.addAttribute("postlike",postlike);

        }
        if(userIn != false) {
            UsersDto usersDto = userService.FindUserDtos(authentication.getName());
            model.addAttribute("UserDto" ,usersDto);
        }
        List<PostDetailDto> postDetailDtosList = postService.findPostDetail();
        PostDto postDto = new PostDto();
        model.addAttribute("languageCss",httpServletRequest.getParameter("find"));
        model.addAttribute("postsDetailList",postDetailDtosList);
        model.addAttribute("postsDto",postDto);
        model.addAttribute("postList",postDtoList);
        model.addAttribute("userIn",userIn);
        return "home";
    }
}
