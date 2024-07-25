package com.ForumLanguage.Forum.service;

import com.ForumLanguage.Forum.dto.CommentsDto;
import com.ForumLanguage.Forum.dto.PostDto;

import java.util.List;

public interface CommentService {

    List<CommentsDto> findComment(String postname);
    void saveComment (CommentsDto commentsDto, String name,String user);

    void saveCommentReply(CommentsDto commentsDto,String name,String user);

    List<CommentsDto> sortTime(List<CommentsDto> list);

    List<CommentsDto> findCommentReplyBycommentnumber(String postname);

    List<CommentsDto> findCommentReply(List<CommentsDto> list, String commentnumber);
}
