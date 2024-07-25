package com.ForumLanguage.Forum.service.implement;

import com.ForumLanguage.Forum.dto.PostDetailDto;
import com.ForumLanguage.Forum.dto.PostDto;
import com.ForumLanguage.Forum.enity.*;
import com.ForumLanguage.Forum.mapper.PostMapper;
import com.ForumLanguage.Forum.repository.*;
import com.ForumLanguage.Forum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PostImplement implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    KindRepository kindRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LikePostRepository likePostRepository;

    @Autowired
    CommentRepository commentRepository;



    @Override
    public void savePost(PostDto postDto,String name) {
        Post post = PostMapper.mapToPostDto(new Post(),postDto);
        String namee = postDto.getLanguage();
        Kind kindId = kindRepository.findIdByLanguage(namee);
        Optional<Account> accountId = accountRepository.findIdByUsername(name);
        Account account = accountId.get();
        User userId = userRepository.findIdByIdAccount(account.getId());
        post.setContent("<br>" + post.getContent().replace("\n", "<br>"));
        post.setIdKind(kindId.getId());
        post.setIdUser(userId.getId());
        int RandomAccNumber = (int) (100000L + new Random().nextInt(9000000));
        int RandomAccNumberq2 = (int) (100000L + new Random().nextInt(900000));
        post.setPostname(RandomAccNumberq2+RandomAccNumber +"");
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

        String hour = now.format(hourFormatter);
        String minute = now.format(minuteFormatter);
        String day = now.format(dayFormatter);
        String month = now.format(monthFormatter);
        String year = now.format(yearFormatter);

        String formattedDateTime = hour + ":" + minute + " - " + day + "/" + month + "/" + year;

        post.setTimepost(formattedDateTime);
        postRepository.save(post);

    }




    @Override
    public List<PostDto> findAllPost() {
        return postRepository.findAllPost();
    }

    @Override
    public PostDto findPostByPostname(String postname) {
       PostDto post = postRepository.findpostByPostname(postname);

       return post;
    }

    @Override
    public List<PostDetailDto> findPostDetail() {
        List<PostDetailDto> list = postRepository.findPostDetailDto();
        return list;
    }
    @Override
    public void deletedPostLike(String postname, String name) {
        Optional<Account> accountId = accountRepository.findIdByUsername(name);
        Account account = accountId.get();
        User user = userRepository.findIdByIdAccount(account.getId());
        Post idPost = postRepository.findPostByPostname(postname);
        List<PostLike> postLikes = likePostRepository.findPostLikeByIdUser(user.getId());
        for(int i = 0 ; i < postLikes.size();i++){
            if(idPost.getId() == postLikes.get(i).getIdPost()){
                likePostRepository.delete(postLikes.get(i));
                break;
            }
        }

    }

    @Override
    public List<String> findPostLike(String username) {
        List<String> strings = new ArrayList<>();
        Optional<Account> accountId = accountRepository.findIdByUsername(username);
        Account account = accountId.get();
        User user = userRepository.findIdByIdAccount(account.getId());
        List<PostLike> postLikes = likePostRepository.findPostLikeByIdUser(user.getId());
        List<Post> posts = postRepository.findAll();

        for (int i = 0; i < postLikes.size() ;i++){
            for (int j = 0; j < posts.size();j++){
                if (postLikes.get(i).getIdPost() == posts.get(j).getId()){
                    strings.add(posts.get(j).getPostname());
                }
            }

        }
        return strings;
    }

    @Override
    public void savePostLike(String postname, String name) {
        Optional<Account> accountId = accountRepository.findIdByUsername(name);
        Account account = accountId.get();
        User user = userRepository.findIdByIdAccount(account.getId());
        Post idPost = postRepository.findPostByPostname(postname);
        PostLike postLike = new PostLike(0,idPost.getId(),user.getId());
        likePostRepository.save(postLike);
    }

    @Override
    public List<PostDto> Search(String title) {
        List<PostDto> postDtoList = new ArrayList<>();
        List<PostDto> list = postRepository.findAllPost();
        for(int i  = 0; i < list.size();i++){
            if(list.get(i).getTitle().contains(title)){
                postDtoList.add(list.get(i));
            }
        }

        return postDtoList;
    }

    @Override
    public List<PostDto> findPostByLanguage(String language) {
        return postRepository.findPostByLanguage(language);
    }

    @Override
    public List<PostDto> findPostSort(String name) {
        List<PostDto> listFinal = new ArrayList<>();
        List<PostDto> listall = postRepository.findAllPost();
        if(name.equals("Popular")){
            List<PostDetailDto> listp = postRepository.findPostDetailDto();
            listp.sort((p1, p2) -> Long.compare(p2.getLikecount(), p1.getLikecount()));
            for(int i  = 0; i < listp.size();i++){
                for(int j = 0; j < listall.size();j++){
                    if(listall.get(j).getPostname().equals(listp.get(i).getPostname())){
                        listFinal.add(listall.get(j));
                    }
                }
            }
            return listFinal;
        }else{
            List<PostDto> listall2 = postRepository.findAllPost();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
            listall2.sort((p1, p2) -> {
                LocalDateTime dateTime1 = LocalDateTime.parse(p1.getTimepost(), formatter);
                LocalDateTime dateTime2 = LocalDateTime.parse(p2.getTimepost(), formatter);
                return dateTime2.compareTo(dateTime1);
            });
            for(int i  = 0; i < listall2.size();i++){
                for(int j = 0; j < listall.size();j++){
                    if(listall.get(j).getPostname().equals(listall2.get(i).getPostname())){
                        listFinal.add(listall.get(j));
                    }
                }
            }

            return listFinal;
        }


    }

    @Override
    public List<PostDto> findPostByusernumber(int usernumber) {
        List<PostDto> postDto = postRepository.findPostByUsernumber(usernumber);

        return postDto;
    }

    @Override
    public void deletedPostByPostName(String postName) {
        Post post = postRepository.findPostByPostname(postName);

        List<Comment> comment = commentRepository.findCommentByIdPost(post.getId());

        if(comment != null ){
            for (Comment commentItem : comment ) {
                commentRepository.delete(commentItem);
            }
        }

        List<PostLike> postLike = likePostRepository.findPostLikeByIdPost(post.getId());
        if(postLike != null){
            for(PostLike postLike1 : postLike) {
                likePostRepository.delete(postLike1);
            }
        }

        postRepository.delete(post);
    }

    @Override
    public Boolean checkPostDeleted(String postname, int username) {
        List<PostDto> postDto = findPostByusernumber(username);
        for(PostDto post : postDto){
            if (post.getPostname().equals(postname)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Post> fillAllPost() {
        return postRepository.findAll();
    }

    @Override
    public int findPostLikeByPostName(String postname) {
        Post post = postRepository.findPostByPostname(postname);
        List<PostLike> postLike = likePostRepository.findPostLikeByIdPost(post.getId());
        int sum = 0;
        for (int i = 0; i < postLike.size();i++){
            sum = sum + 1;
        }
        return sum;
    }

    @Override
    public boolean checkLike(String postname, String username) {
         Optional<Account> account = accountRepository.findIdByUsername(username);
         Account accountId = account.get();
         User user = userRepository.findIdByIdAccount(accountId.getId());
         Post Post = postRepository.findPostByPostname(postname);
         List<PostLike> postLike = likePostRepository.findPostLikeByIdPost(Post.getId());
         for (int i = 0 ; i < postLike.size() ; i++){
             if(postLike.get(i).getIdUser() == user.getId()){
                 return true;
             }
         }
        return false;
    }



}
