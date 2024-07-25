package com.ForumLanguage.Forum.repository;

import com.ForumLanguage.Forum.enity.Email;
import com.ForumLanguage.Forum.enity.Kind;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KindRepository extends JpaRepository<Kind,Integer> {

    Kind findIdByLanguage(String language);
}
