package com.ForumLanguage.Forum.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountException.class)
    public String handleAccountException(AccountException ex, RedirectAttributes redirectAttributes) {
        String errorMessage = ex.getMessage();
        redirectAttributes.addAttribute("error", errorMessage);
        return "redirect:/Register";
    }
}
