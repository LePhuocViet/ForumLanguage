package com.ForumLanguage.Forum.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CommentsDto {
    private String commentnumber;
    private String timecomment;
    private String commentcontent;
    private String name;
    private String postname;


    public CommentsDto(String commentnumber, String timecomment, String commentcontent, String name, String postname) {
        this.commentnumber = commentnumber;
        this.timecomment = timecomment;
        this.commentcontent = commentcontent;
        this.name = name;
        this.postname = postname;
    }

    public CommentsDto() {

    }
}
