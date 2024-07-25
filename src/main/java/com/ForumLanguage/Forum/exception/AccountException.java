package com.ForumLanguage.Forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountException extends RuntimeException{
    public AccountException(String message){
        super(message);
    }
}
