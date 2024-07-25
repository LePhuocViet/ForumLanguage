package com.ForumLanguage.Forum.mapper;

import com.ForumLanguage.Forum.dto.AccountsDto;
import com.ForumLanguage.Forum.dto.KindDto;
import com.ForumLanguage.Forum.dto.PostDto;
import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.enity.Account;
import com.ForumLanguage.Forum.enity.Kind;
import com.ForumLanguage.Forum.enity.Post;
import com.ForumLanguage.Forum.enity.User;

import java.sql.Time;

public class PostMapper {
    public  static PostDto mapToPosts(Post post, PostDto postDto){
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setTimepost(post.getTimepost());

        return postDto;
    }

    public  static Post mapToPostDto(Post post, PostDto postDto){
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTimepost(postDto.getTimepost());

        return post;
    }
}
