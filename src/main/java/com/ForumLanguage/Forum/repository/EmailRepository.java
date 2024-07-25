package com.ForumLanguage.Forum.repository;

import com.ForumLanguage.Forum.enity.Account;
import com.ForumLanguage.Forum.enity.Email;
import com.ForumLanguage.Forum.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email,String> {
    Optional<Email> findCodeByEmail(String email);

    Optional<Email> findByEmail(String email);
}
