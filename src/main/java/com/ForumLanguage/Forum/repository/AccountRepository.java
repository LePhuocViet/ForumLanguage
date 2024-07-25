package com.ForumLanguage.Forum.repository;

import com.ForumLanguage.Forum.enity.Account;
import com.ForumLanguage.Forum.enity.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findIdByUsername(String username);

}
