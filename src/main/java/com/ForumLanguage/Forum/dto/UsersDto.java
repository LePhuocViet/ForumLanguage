package com.ForumLanguage.Forum.dto;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class UsersDto {

    private int usernumber;
    private String name;


    private String email;


    private String sex;


    private String language;

    public UsersDto(int usernumber, String name, String email, String sex, String language) {
        this.usernumber = usernumber;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.language = language;
    }
    public UsersDto() {

    }
}
