package com.ForumLanguage.Forum.repository;

import com.ForumLanguage.Forum.dto.CommentsDto;
import com.ForumLanguage.Forum.enity.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentReplyRepository extends JpaRepository<CommentReply,Integer> {

    @Query("SELECT NEW com.ForumLanguage.Forum.dto.CommentsDto(c.commentnumber, cr.timecomment, cr.commentcontent, u.name,p.postname) " +
            "FROM CommentReply cr " +
            "JOIN User u ON cr.idUser = u.id " +
            "JOIN Comment c ON cr.idComment = c.id " +
            "JOIN Post p ON c.idPost = p.id " +
            "WHERE p.postname = :postname")
    List<CommentsDto> findCommentReplyBycommentnumber(@Param("postname") String postname);
}
