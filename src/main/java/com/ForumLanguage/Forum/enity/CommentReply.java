package com.ForumLanguage.Forum.enity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "commentreply")
public class CommentReply {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "timecomment")
    private String timecomment;

    @Column(name = "comment_content")
    private String commentcontent;

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_comment")
    private int idComment;

}