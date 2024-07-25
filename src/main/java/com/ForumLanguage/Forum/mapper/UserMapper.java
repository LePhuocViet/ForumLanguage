package com.ForumLanguage.Forum.mapper;

import com.ForumLanguage.Forum.dto.AccountsDto;
import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.enity.Account;
import com.ForumLanguage.Forum.enity.User;

public class UserMapper {
    public  static UsersDto mapToUsers(User user, UsersDto usersDto){
        usersDto.setUsernumber(user.getUsernumber());
        usersDto.setName(user.getName());
        usersDto.setEmail(user.getEmail());
        usersDto.setLanguage(user.getLanguage());
        usersDto.setSex(user.getSex());
        return usersDto;
    }

    public  static User mapToUsersDto(User user, UsersDto usersDto){
        user.setUsernumber(usersDto.getUsernumber());
        user.setName(usersDto.getName());
        user.setEmail(usersDto.getEmail());
        user.setLanguage(usersDto.getLanguage());
        user.setSex(usersDto.getSex());
        return user;
    }
}
