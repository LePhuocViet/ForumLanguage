package com.ForumLanguage.Forum.service;

import com.ForumLanguage.Forum.dto.PostDetailDto;
import com.ForumLanguage.Forum.dto.PostDto;
import com.ForumLanguage.Forum.enity.Post;

import java.util.List;

public interface PostService {

void savePost(PostDto postDto,String name);
List<PostDto> findAllPost();

PostDto findPostByPostname(String postname);

List<PostDetailDto> findPostDetail();

void savePostLike(String postname, String name);
void deletedPostLike(String postname, String name);

List<PostDto> Search(String title);

List<PostDto> findPostByLanguage(String language);

List<PostDto> findPostSort(String name);

List<PostDto> findPostByusernumber(int usernumber);

void deletedPostByPostName(String postName);

Boolean checkPostDeleted(String postname,int username);

List<Post> fillAllPost();

int findPostLikeByPostName(String postname);

boolean checkLike(String postname, String username);

List<String> findPostLike(String username);

}
