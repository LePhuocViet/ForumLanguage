package com.ForumLanguage.Forum.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
public class PostDetailDto {
    private long likecount;

    private long commentcount;

    private String postname;

    public PostDetailDto(long likecount, long commentcount, String postname) {
        this.likecount = likecount;
        this.commentcount = commentcount;
        this.postname = postname;
    }
    public PostDetailDto() {

    }



}
