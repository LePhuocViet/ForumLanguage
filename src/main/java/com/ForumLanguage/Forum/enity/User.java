package com.ForumLanguage.Forum.enity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter@Getter@ToString
@AllArgsConstructor@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "usernumber")
    private int usernumber;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "sex")
    private String sex;

    @Column(name = "language")
    private String language;

    @Column(name = "id_account")
    private int idAccount;



}
