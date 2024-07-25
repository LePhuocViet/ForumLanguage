package com.ForumLanguage.Forum.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Time;

@Data
public class PostDto {


    private String postname;

    private String title;

    private String content;

    private String timepost;

    private String name;

    private String language;

    private int usernumber;


    public PostDto(String postname, String title, String content, String timepost, String name, String language, int usernumber) {
        this.postname = postname;
        this.title = title;
        this.content = content;
        this.timepost = timepost;
        this.name = name;
        this.language = language;
        this.usernumber = usernumber;
    }

    public PostDto() {

    }

}
