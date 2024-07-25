package com.ForumLanguage.Forum.mapper;

import com.ForumLanguage.Forum.dto.AccountsDto;
import com.ForumLanguage.Forum.enity.Account;

public class AccountMapper {
    public  static AccountsDto mapToAccounts(Account account, AccountsDto accountsDto){
        accountsDto.setUsername(account.getUsername());
        accountsDto.setPassword(account.getPassword());
        return accountsDto;
    }

    public  static Account mapToAccountsDto(Account account, AccountsDto accountsDto){
        account.setUsername(accountsDto.getUsername());
        account.setPassword(accountsDto.getPassword());
        return account;
    }
}
