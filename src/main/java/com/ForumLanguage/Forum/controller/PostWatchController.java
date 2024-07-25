package com.ForumLanguage.Forum.controller;

import com.ForumLanguage.Forum.dto.CommentsDto;
import com.ForumLanguage.Forum.dto.PostDetailDto;
import com.ForumLanguage.Forum.dto.PostDto;
import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.enity.Post;
import com.ForumLanguage.Forum.service.CommentService;
import com.ForumLanguage.Forum.service.PostService;
import com.ForumLanguage.Forum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostWatchController {
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @GetMapping("/Posts")
    public String postWatch(HttpServletRequest httpServletRequest,Model model,Authentication authentication) {
        boolean userIn = authentication != null && authentication.isAuthenticated() ;
        String name  = httpServletRequest.getParameter("p");
        PostDto postDto = postService.findPostByPostname(name);
        if(userIn != false) {
            List<String> postlike = postService.findPostLike(authentication.getName());
            model.addAttribute("postlike",postlike);
        } else{
            List<String> postlike = new ArrayList<>();
            postlike.add(1+"");
            model.addAttribute("postlike",postlike);
        }
        List<CommentsDto> commentsDtos = commentService.findComment(name);
        List<CommentsDto> commentsDtoList = commentService.findCommentReplyBycommentnumber(name);
        CommentsDto newcomment= new CommentsDto();
        newcomment.setPostname(name);
        CommentsDto newcommentreply = new CommentsDto();
        newcommentreply.setPostname(name);
        List<PostDetailDto> postDetailDtosList = postService.findPostDetail();
        model.addAttribute("postsDetailList",postDetailDtosList);
        model.addAttribute("postDto",postDto);
        model.addAttribute("commentDto",commentsDtos);
        model.addAttribute("commentDtore",commentsDtoList);
        model.addAttribute("newcomment",newcomment);
        model.addAttribute("newcommentreply",newcommentreply);
        model.addAttribute("userIn",userIn);
        return "post_watch";
    }

    @PostMapping("/Comment")
    @ResponseBody
    public String comment(@ModelAttribute CommentsDto commentsDto, Model model, HttpServletRequest httpServletRequest, Authentication authentication){
        commentService.saveComment(commentsDto,httpServletRequest.getParameter("c"),authentication.getName());
        List<CommentsDto> commentsDtos = commentService.findComment(httpServletRequest.getParameter("c"));
        model.addAttribute("commentDto", commentsDtos);
        return "";
    }

    @PostMapping("/Reply")
    @ResponseBody
    public String reply(@ModelAttribute CommentsDto commentsDto, Model model, HttpServletRequest httpServletRequest, Authentication authentication){
        commentService.saveCommentReply(commentsDto,httpServletRequest.getParameter("cr"),authentication.getName());
        List<CommentsDto> commentsDtoList = commentService.findCommentReplyBycommentnumber(httpServletRequest.getParameter("cp"));
        List<CommentsDto> commentsDtoListFinal = commentService.findCommentReply(commentsDtoList,httpServletRequest.getParameter("cr"));
        model.addAttribute("commentDtore",commentsDtoListFinal);
        return "";
    }

    @GetMapping("/deletePost")
    public String deletePost(HttpServletRequest httpServletRequest, Authentication authentication, RedirectAttributes redirectAttributes){
        boolean userIn = authentication != null && authentication.isAuthenticated() ;
        if (userIn != true){
            return "login";
        }
        UsersDto usersDto = userService.FindUserDtos(authentication.getName());
        int number = usersDto.getUsernumber();
        if (postService.checkPostDeleted(httpServletRequest.getParameter("dp"),usersDto.getUsernumber()) == true){
            postService.deletedPostByPostName(httpServletRequest.getParameter("dp"));
        }
        redirectAttributes.addAttribute("u", number);
        return "redirect:/User";
    }
}
