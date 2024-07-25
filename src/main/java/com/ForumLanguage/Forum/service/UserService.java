package com.ForumLanguage.Forum.service;

import com.ForumLanguage.Forum.dto.UsersDto;

import java.util.List;

public interface UserService {

    void delete(String name);
    void save(UsersDto usersDto,String name);

    String checkEmail (String email);

    UsersDto FindUser(String name);

    UsersDto FindUserDtos(String name);

    UsersDto findUserByUsernumber(int usernumber);

    UsersDto findUserByUsername(String username);

    void saveUser(String username,String name);



}
