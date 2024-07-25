package com.ForumLanguage.Forum.repository;

import com.ForumLanguage.Forum.dto.CommentsDto;
import com.ForumLanguage.Forum.dto.PostDetailDto;
import com.ForumLanguage.Forum.enity.Account;
import com.ForumLanguage.Forum.enity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query("SELECT NEW com.ForumLanguage.Forum.dto.CommentsDto(c.commentnumber, c.timecomment, c.commentcontent, u.name,p.postname) " +
            "FROM Comment c " +
            "JOIN User u ON c.idUser = u.id " +
            "JOIN Post p ON c.idPost = p.id " +
            "WHERE p.postname = :postname")
    List<CommentsDto> findCommentByPostname(@Param("postname") String postname);


    Optional<Comment> findIdByCommentnumber(String commentnumber);

    List<Comment> findCommentByIdPost(int idPost);
}
