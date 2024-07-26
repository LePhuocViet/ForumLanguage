package com.ForumLanguage.Forum.service.implement;

import com.ForumLanguage.Forum.dto.AccountsDto;
import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.enity.Account;
import com.ForumLanguage.Forum.enity.Email;
import com.ForumLanguage.Forum.enity.Role;
import com.ForumLanguage.Forum.enity.User;
import com.ForumLanguage.Forum.exception.AccountException;
import com.ForumLanguage.Forum.mapper.AccountMapper;
import com.ForumLanguage.Forum.mapper.UserMapper;
import com.ForumLanguage.Forum.repository.AccountRepository;
import com.ForumLanguage.Forum.repository.EmailRepository;
import com.ForumLanguage.Forum.repository.RoleRepository;
import com.ForumLanguage.Forum.repository.UserRepository;
import com.ForumLanguage.Forum.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor

public class AccountImplement implements AccountService {
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private EmailRepository emailRepository;

    @Override
    public void CreateAccount(AccountsDto accountsDto,String email,String code) {
        Account account = AccountMapper.mapToAccountsDto(new Account(),accountsDto);

       Optional<Account> byUserName = accountRepository.findByUsername(accountsDto.getUsername());
       if(byUserName.isPresent()){
            throw new AccountException("Username is exist");
        }else if(accountsDto.getUsername().length() <=  5 || accountsDto.getUsername().length() >= 10){
            throw new AccountException("Username must be at least 5 characters long and at max 10");
        } else if(!accountsDto.getUsername().matches("^[a-zA-Z0-9_]*$")){
           throw new AccountException("Username is wrong");
       }

        if(!accountsDto.getPassword().matches("^[a-zA-Z0-9_]*$") ){
            throw new AccountException("Password is wrong");
        } else if(accountsDto.getPassword().length() <=  8 || accountsDto.getPassword().length() >= 20){
            throw new AccountException("Password must be at least 8 characters long and at max 20");
        }
        Optional<User> byEmailCheck= userRepository.findByEmail(email);
        if(byEmailCheck.isPresent()){
            throw new AccountException("Email is exist");
        } else if(!isValidEmail(email)){
            throw new AccountException("Email is wrong");
        }

        Optional<Email> byEmail=  emailRepository.findCodeByEmail(email);
        if(byEmail.isPresent()) {
            Email emailCode = byEmail.get();
            int userCode = Integer.valueOf(code).intValue();
            if (emailCode.getCode() != userCode && emailCode.countsign < 8) {
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                Runnable deleteEmailCodeTask = () -> {
                    emailRepository.delete(emailCode);
                };

                executorService.schedule(deleteEmailCodeTask, 10, TimeUnit.MINUTES);
                executorService.shutdown();
                emailCode.setCountsign(emailCode.getCountsign() + 1);
                emailRepository.save(emailCode);
                throw new AccountException("Wrong Code");
            } else if(emailCode.getCountsign() == 8){
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                Runnable deleteEmailCodeTask = () -> {
                    emailRepository.delete(emailCode);
                };

                executorService.schedule(deleteEmailCodeTask, 5, TimeUnit.MINUTES);
                executorService.shutdown();
                throw new AccountException("Too Many Wait 5 Minutes");
            }

            else {
                emailRepository.delete(emailCode);
            }
        } else {
            throw new AccountException("Wrong Code");
        }


        String authPassword = passwordEncoder.encode(accountsDto.getPassword());
        account.setPassword(authPassword);
        Account savedAccount = accountRepository.save(account);
        roleRepository.save(createNewRole(account));
        userRepository.save(createNewUser(savedAccount,email));
    }


    private Role createNewRole(Account account){
        Role role = new Role();
        role.setIdAccount(account.getId());
        role.setRole("ROLE_USER");
        return role;
    }

    private User createNewUser(Account account, String email){
        User newUser = new User();
        newUser.setIdAccount(account.getId());
        newUser.setEmail(email);
        newUser.setSex("None");
        newUser.setLanguage("English");
        int RandomAccNumber = (int) (10000L + new Random().nextInt(900000));
        int RandomAccNumberq2 = (int) (1000L + new Random().nextInt(9000));
        newUser.setName("#user" + RandomAccNumber +RandomAccNumberq2);
        return newUser;
    }

    public String checkPassword(AccountsDto accountsDto , String password){
        if(!accountsDto.getPassword().equals(password)){
            throw new AccountException("Password does not match");
        }
        return "";
    }

    @Override
    public boolean checkAuthentication(List<String> list) {
        for (int i  = 0; i < list.size() ;i++){
            if (list.get(i).equals("ROLE_ADMIN")){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Account> fillAllAccount() {
        return accountRepository.findAll();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "[a-zA-Z0-9_+&*-]+@gmail.com";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
