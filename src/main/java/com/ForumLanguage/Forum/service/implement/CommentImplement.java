package com.ForumLanguage.Forum.service.implement;

import com.ForumLanguage.Forum.dto.CommentsDto;
import com.ForumLanguage.Forum.dto.PostDto;
import com.ForumLanguage.Forum.enity.*;
import com.ForumLanguage.Forum.repository.*;
import com.ForumLanguage.Forum.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class CommentImplement implements CommentService {

    CommentRepository commentRepository;
    PostRepository postRepository;
    AccountRepository accountRepository;
    UserRepository userRepository;
    CommentReplyRepository commentReplyRepository;
    @Override
    public List<CommentsDto> findComment(String postname) {
        List<CommentsDto> commentsDtos = commentRepository.findCommentByPostname(postname);

        return commentsDtos;
    }

    @Override
    public void saveComment(CommentsDto commentsDto,String name,String user) {
        Comment comment = new Comment();
        comment.setCommentcontent(commentsDto.getCommentcontent());
        Post idPost = postRepository.findPostByPostname(name);
        Optional<Account> accountId = accountRepository.findIdByUsername(user);
        Account account = accountId.get();
        User userId = userRepository.findIdByIdAccount(account.getId());
        comment.setIdUser(userId.getId());
        comment.setIdPost(idPost.getId());
        int RandomAccNumber = (int) (10000L + new Random().nextInt(900000));
        int RandomAccNumberq2 = (int) (1000L + new Random().nextInt(9000));
        comment.setCommentnumber(RandomAccNumber + 0 + RandomAccNumberq2 +"");
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
        comment.setTimecomment(formattedDateTime);
        commentRepository.save(comment);
    }

    @Override
    public void saveCommentReply(CommentsDto commentsDto, String name, String user) {
        CommentReply commentReply = new CommentReply();
        Optional<Account> accountId = accountRepository.findIdByUsername(user);
        Account account = accountId.get();
        User userId = userRepository.findIdByIdAccount(account.getId());
        commentReply.setIdUser(userId.getId());
        commentReply.setCommentcontent(commentsDto.getCommentcontent());
        Optional<Comment> idcomment=commentRepository.findIdByCommentnumber(name);
        Comment comment = idcomment.get();
        commentReply.setIdComment(comment.getId());
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = String.format("%02d:%02d - %02d/%02d/%d",
                now.getHour(),
                now.getMinute(),
                now.getDayOfMonth(),
                now.getMonthValue(),
                now.getYear());
        comment.setTimecomment(formattedTime);
    }

    public  List<CommentsDto> sortTime(List<CommentsDto> list) {

        Comparator<CommentsDto> timeComparator = new Comparator<CommentsDto>() {
            @Override
            public int compare(CommentsDto comment1, CommentsDto comment2) {

                LocalDateTime time1 = parseTime(comment1.getTimecomment());
                LocalDateTime time2 = parseTime(comment2.getTimecomment());

                return time2.compareTo(time1);
            }
        };

        Collections.sort(list, timeComparator);

        return list;
    }

    @Override
    public List<CommentsDto> findCommentReplyBycommentnumber(String postname) {
        return commentReplyRepository.findCommentReplyBycommentnumber(postname);
    }

    @Override
    public List<CommentsDto> findCommentReply(List<CommentsDto> list, String commentnumber) {
        List<CommentsDto> list1 = new ArrayList<>();
        for (int i  =0; i < list.size() ; i++){
            if(list.get(i).getCommentnumber() == commentnumber){
                list1.add(list.get(i));
            }
        }
        return list1;
    }


    private LocalDateTime parseTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        return LocalDateTime.parse(timeString, formatter);
    }
}
