package com.blogging.blogapplication.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogging.blogapplication.Payloads.CommentDto;
import com.blogging.blogapplication.Security.UserOnlyRequest;
import com.blogging.blogapplication.Services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private UserOnlyRequest userOnlyRequest;

    @PostMapping("/{postid}/user/{userid}")
    ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto comment, @PathVariable Long postid,
            @PathVariable Long userid, @RequestHeader(name = "Authorization") String token) {

        this.userOnlyRequest.allow(token, userid);
        CommentDto resp = commentService.createComment(comment, postid, userid);
        return new ResponseEntity<>(resp, null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentid}/user/{userid}")
    ResponseEntity<String> deleteComment(@PathVariable Long commentid, @PathVariable Long userid,
            @RequestHeader(name = "Authorization") String token,
            SecurityContextHolderAwareRequestWrapper screq) {

        if (!screq.isUserInRole("ROLE_ADMIN"))
            this.userOnlyRequest.allow(token, userid);

        commentService.deleteComment(commentid, userid);
        return new ResponseEntity<>("Comment Deleted Successfully", null, HttpStatus.OK);

    }

    @GetMapping("/user/{userid}")
    ResponseEntity<List<CommentDto>> getAllUserComments(@PathVariable Long userid) {
        List<CommentDto> getComments = commentService.getAllUserComments(userid);
        return new ResponseEntity<>(getComments, null, HttpStatus.OK);
    }

    @GetMapping("/post/{postid}")
    ResponseEntity<List<CommentDto>> getAllPostComments(@PathVariable Long postid) {
        List<CommentDto> getComments = commentService.getAllPostComments(postid);
        return new ResponseEntity<>(getComments, null, HttpStatus.OK);
    }
}
