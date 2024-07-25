package com.ForumLanguage.Forum.repository;

import com.ForumLanguage.Forum.dto.PostDetailDto;
import com.ForumLanguage.Forum.dto.PostDto;
import com.ForumLanguage.Forum.enity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

   @Query("SELECT NEW com.ForumLanguage.Forum.dto.PostDto(p.postname,p.title, p.content, p.timepost, u.name, k.language,u.usernumber) " +
           "FROM Post p JOIN User u ON p.idUser = u.id JOIN Kind k ON p.idKind = k.id")
   List<PostDto> findAllPost();

   List<Post> findPostByIdUser(int idUser);
   @Query("SELECT NEW com.ForumLanguage.Forum.dto.PostDto(p.postname, p.title, p.content, p.timepost, u.name, k.language,u.usernumber) " +
           "FROM Post p JOIN User u ON p.idUser = u.id JOIN Kind k ON p.idKind = k.id " +
           "WHERE p.postname = :postname")
   PostDto findpostByPostname(@Param("postname") String postname);

   @Query("SELECT NEW com.ForumLanguage.Forum.dto.PostDetailDto(" +
           "COUNT(DISTINCT pl.id), " +
           "COUNT(DISTINCT c.id), " +
           "p.postname) " +
           "FROM Post p " +
           "LEFT JOIN Comment c ON c.idPost = p.id " +
           "LEFT JOIN PostLike pl ON pl.idPost = p.id " +
           "GROUP BY p.postname")
   List<PostDetailDto> findPostDetailDto();

   Post findPostByPostname(String postname);
   @Query("SELECT NEW com.ForumLanguage.Forum.dto.PostDto(p.postname, p.title, p.content, p.timepost, u.name, k.language,u.usernumber)" +
           "FROM Post p JOIN User u ON p.idUser = u.id JOIN Kind k ON p.idKind = k.id " +
           "WHERE k.language = :language")
   List<PostDto> findPostByLanguage(String language);

   @Query("SELECT NEW com.ForumLanguage.Forum.dto.PostDto(p.postname, p.title, p.content, p.timepost, u.name, k.language,u.usernumber)" +
           "FROM Post p JOIN User u ON p.idUser = u.id JOIN Kind k ON p.idKind = k.id " +
           "WHERE u.usernumber = :usernumber")
   List<PostDto> findPostByUsernumber(int usernumber);


}
