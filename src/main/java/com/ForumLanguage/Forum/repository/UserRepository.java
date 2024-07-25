package com.ForumLanguage.Forum.repository;

import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    User findIdByIdAccount(int idAccount);

    User findUserByIdAccount(int idAccount);
    @Query("SELECT NEW com.ForumLanguage.Forum.dto.UsersDto(u.usernumber, u.name, u.email, u.sex, u.language) " +
            "FROM User u JOIN  Account a ON u.idAccount = a.id " +
            "WHERE a.username = :name")
    UsersDto findUserByName(@Param("name") String name);

    User findUserByUsernumber(int usernumber);

}
