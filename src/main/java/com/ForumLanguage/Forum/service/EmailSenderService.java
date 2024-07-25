package com.ForumLanguage.Forum.service;

import com.ForumLanguage.Forum.enity.Account;
import com.ForumLanguage.Forum.enity.Email;
import com.ForumLanguage.Forum.exception.AccountException;
import com.ForumLanguage.Forum.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailSenderService {
    private static Date lastSentTime = null;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail, String subject) {
        int countsend = 0;
        int countsign = 0;
        Date currentTime = new Date();
        if (lastSentTime == null || currentTime.getTime() - lastSentTime.getTime() >= TimeUnit.SECONDS.toMillis(30)) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("vietyts2003@gail.com");
            mailMessage.setTo(toEmail);
            int RandomAccNumber = (int) (10000L + new Random().nextInt(900000));
            mailMessage.setText(RandomAccNumber + "");
            mailMessage.setSubject(subject);

            Optional<Email> byEmail = emailRepository.findByEmail(toEmail);
            if (byEmail.isPresent()) {
                Email email = byEmail.get();
                countsend = email.getCountsend();
                countsign = email.getCountsign();
                emailRepository.delete(email);
            }
            countsend++;
            Email newEmail = new Email(toEmail, RandomAccNumber,countsend,countsign);
            emailRepository.save(newEmail);

            try {
                mailSender.send(mailMessage);
                lastSentTime = currentTime;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {


        }
    }
}
