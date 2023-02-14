package com.blogging.blogapplication.Services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.blogapplication.Entities.*;
import com.blogging.blogapplication.Exceptions.ForbiddenException;
import com.blogging.blogapplication.Exceptions.ResourceNotFoundException;
import com.blogging.blogapplication.Payloads.CommentDto;
import com.blogging.blogapplication.Repositories.CommentRepository;
import com.blogging.blogapplication.Repositories.PostRepository;
import com.blogging.blogapplication.Repositories.UserRepository;
import com.blogging.blogapplication.Services.CommentService;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepo;

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto comment, Long postid, Long userid) {

        Post getPost = postRepo.findById(postid).orElseThrow(() -> {
            return new ResourceNotFoundException("Post", "id", postid);
        });

        User getUser = userRepo.findById(userid).orElseThrow(() -> {
            return new ResourceNotFoundException("User", "id", userid);
        });

        Comment mycomment = modelMapper.map(comment, Comment.class);

        mycomment.setPost(getPost);
        mycomment.setUser(getUser);

        commentRepo.save(mycomment);

        CommentDto commentResp = modelMapper.map(mycomment, CommentDto.class);

        return commentResp;
    }

    @Override
    public void deleteComment(Long commentid, Long userid) {

        Comment comm = commentRepo.findById(commentid).orElseThrow(() -> {
            return new ResourceNotFoundException("comment", "id", commentid);
        });

        if (comm.getUser().getId() != userid)
            throw new ForbiddenException("Comment userid mismatch");

        commentRepo.delete(comm);

    }

    @Override
    public List<CommentDto> getAllUserComments(Long userid) {

        User user = userRepo.findById(userid).orElseThrow(() -> {
            return new ResourceNotFoundException("User", "id", userid);
        });

        List<Comment> commlist = commentRepo.findByUser(user);

        List<CommentDto> respList = commlist.stream().map((e) -> modelMapper.map(e, CommentDto.class))
                .collect(Collectors.toList());
        return respList;
    }

    @Override
    public List<CommentDto> getAllPostComments(Long postid) {

        Post post = postRepo.findById(postid).orElseThrow(() -> {
            return new ResourceNotFoundException("Post", "id", postid);
        });

        List<Comment> commlist = commentRepo.findByPost(post);
        List<CommentDto> respList = commlist.stream().map((e) -> modelMapper.map(e, CommentDto.class))
                .collect(Collectors.toList());
        return respList;
    }

}
