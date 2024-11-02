package com.ForumLanguage.Forum.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AccountsDto {

    private String username;
    private String password;

}
