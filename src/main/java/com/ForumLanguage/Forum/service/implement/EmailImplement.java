package com.ForumLanguage.Forum.service.implement;

import com.ForumLanguage.Forum.enity.Email;
import com.ForumLanguage.Forum.repository.EmailRepository;
import com.ForumLanguage.Forum.repository.UserRepository;
import com.ForumLanguage.Forum.service.EmailSenderService;
import com.ForumLanguage.Forum.service.EmailService;
import com.ForumLanguage.Forum.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class EmailImplement implements EmailService {

    private EmailRepository emailRepository;

    private UserService userService;

    private EmailSenderService emailSenderService;
    @Override
    public void Save(Email email) {
        emailRepository.save(email);
    }

    @Override
    public void Delete(Email email) {
        emailRepository.delete(email);
    }

    @Override
    public boolean checkCountSend(String email) {
        Optional<Email> byEmail = emailRepository.findByEmail(email);
        if(!byEmail.isPresent()){
            return true;
        }
        Email emailsend = byEmail.get();
        if(emailsend.getCountsend() == 8 ){
            emailsend.setCountsend(9);
            emailRepository.save(emailsend);
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            Runnable deleteEmailCodeTask = () -> {
                emailsend.setCountsend(0);
                emailRepository.save(emailsend);
            };
            executorService.schedule(deleteEmailCodeTask, 5, TimeUnit.MINUTES);
            executorService.shutdown();
            return false;
        } else if(emailsend.getCountsend()==9){
            return false;
        }
        return true;
    }



    public String checkEmail(String email){
        if(email != null) {
            if (userService.checkEmail(email).equals("true")) {
                if (checkCountSend(email) == true) {
                    emailSenderService.sendMail(email, "Code ForumLanguage");
                    return "Code Was Being Send";
                } else {
                    return "Too Much Wait 5 Minute To Try Again";
                }

            } else {
                return userService.checkEmail(email);
            }
        } else {
            return "Enter Email";
        }
    }


}
