package com.ForumLanguage.Forum.enity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emailcode")
public class Email {


    @Id
    @Column(name = "email")
    public String email;

    @Column(name = "code")
    public int code;

    @Column(name = "countsend")
    public int countsend;

    @Column(name = "countsign")
    public int countsign;
}
