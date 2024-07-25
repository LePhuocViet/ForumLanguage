package com.ForumLanguage.Forum.enity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "postname")
    private String postname;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name ="timepost")
    private String timepost;

    @JoinColumn(name = "id_user")
    private int idUser;

    @Column(name = "id_kind")
    private int idKind;

}
