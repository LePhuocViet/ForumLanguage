package com.ForumLanguage.Forum.repository;

import com.ForumLanguage.Forum.enity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikePostRepository extends JpaRepository<PostLike,Integer> {

    List<PostLike> findPostLikeByIdPost(int idPost);

    List<PostLike> findPostLikeByIdUser(int idUser);



}
