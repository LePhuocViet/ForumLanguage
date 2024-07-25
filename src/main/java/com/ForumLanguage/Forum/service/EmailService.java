package com.ForumLanguage.Forum.service;

import com.ForumLanguage.Forum.enity.Email;

public interface EmailService {
    void Save(Email email);

    void Delete(Email email);

    boolean checkCountSend(String email);

    String checkEmail(String email);
}
