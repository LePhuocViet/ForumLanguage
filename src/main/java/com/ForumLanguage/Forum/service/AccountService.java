package com.ForumLanguage.Forum.service;

import com.ForumLanguage.Forum.dto.AccountsDto;
import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.enity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    void CreateAccount(AccountsDto accountsDto, String email,String code);

    String checkPassword(AccountsDto accountsDto, String password);

    boolean checkAuthentication(List<String> list);

    List<Account> fillAllAccount();
}
