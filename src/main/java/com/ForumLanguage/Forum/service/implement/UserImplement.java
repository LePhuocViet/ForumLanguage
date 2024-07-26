package com.ForumLanguage.Forum.service.implement;

import com.ForumLanguage.Forum.dto.UsersDto;
import com.ForumLanguage.Forum.enity.Account;
import com.ForumLanguage.Forum.enity.Post;
import com.ForumLanguage.Forum.enity.Role;
import com.ForumLanguage.Forum.enity.User;
import com.ForumLanguage.Forum.exception.AccountException;
import com.ForumLanguage.Forum.mapper.UserMapper;
import com.ForumLanguage.Forum.repository.AccountRepository;
import com.ForumLanguage.Forum.repository.PostRepository;
import com.ForumLanguage.Forum.repository.RoleRepository;
import com.ForumLanguage.Forum.repository.UserRepository;
import com.ForumLanguage.Forum.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserImplement implements UserService {
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private RoleRepository roleRepository;

    @Override
    public void delete(String name) {

        Optional<Account> accountId = accountRepository.findIdByUsername(name);
        Account account = accountId.get();
        User userId = userRepository.findIdByIdAccount(account.getId());
        User username = userRepository.findUserByIdAccount(userId.getIdAccount());
        Role role = roleRepository.findByIdAccount(account.getId()) ;
        List<Post> postList = postRepository.findPostByIdUser(userId.getId());
        for(int i = 0; i < postList.size() ; i++){
            postRepository.delete(postList.get(i));
        }
        userRepository.delete(username);
        roleRepository.delete(role);
        accountRepository.delete(account);



    }

    @Override
    public void save(UsersDto usersDto,String name) {
        Optional<Account> accountId = accountRepository.findIdByUsername(name);
        Account account = accountId.get();
       User userId = userRepository.findIdByIdAccount(account.getId());

        User username = userRepository.findUserByIdAccount(userId.getIdAccount());
        username.setSex(usersDto.getSex());
        username.setLanguage(usersDto.getLanguage());
        int RandomAccNumber = (int) (100000L + new Random().nextInt(9000000));
        int RandomAccNumberq2 = (int) (100000L + new Random().nextInt(900000));
        username.setUsernumber(RandomAccNumber+RandomAccNumberq2);
        userRepository.save(username);

    }

    @Override
    public String checkEmail(String email) {
        Optional<User> byEmail= userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            return "Email is exits";
        } else if(!isValidEmail(email)){
            return "Email is wrong";
        } else {
            return "true";
        }
    }

    @Override
    public UsersDto FindUser(String name) {
        Optional<Account> accountId = accountRepository.findIdByUsername(name);
        Account account = accountId.get();
        User userId = userRepository.findIdByIdAccount(account.getId());
        User username = userRepository.findUserByIdAccount(userId.getIdAccount());
        UsersDto usersDto = UserMapper.mapToUsers(username,new UsersDto());
        return usersDto;
    }

    @Override
    public UsersDto FindUserDtos(String name) {
        UsersDto usersDto = new UsersDto();
        if(name==null){
            return usersDto;
        } else{
            usersDto = userRepository.findUserByName(name);
            return usersDto;
        }

    }

    @Override
    public UsersDto findUserByUsernumber(int usernumber) {
        User user = userRepository.findUserByUsernumber(usernumber);

        UsersDto userDtos = UserMapper.mapToUsers(user,new UsersDto());
        return userDtos;
    }

    @Override
    public UsersDto findUserByUsername(String username) {
        UsersDto usersDto = userRepository.findUserByName(username);
        return usersDto;
    }

    @Override
    public void saveUser(String username, String name) {
        Optional<Account> accountId = accountRepository.findIdByUsername(username);
        Account account = accountId.get();
        User userId = userRepository.findIdByIdAccount(account.getId());
        User user = userRepository.findUserByIdAccount(userId.getIdAccount());
        user.setName(name);
        userRepository.save(user);
    }

    @Override
    public List<User> fillAll() {
        return userRepository.findAll();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "[a-zA-Z0-9_+&*-]+@gmail.com";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
