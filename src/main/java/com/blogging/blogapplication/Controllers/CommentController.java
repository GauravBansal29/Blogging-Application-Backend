package com.blogging.blogapplication.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogging.blogapplication.Payloads.CommentDto;
import com.blogging.blogapplication.Services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/{postid}")
    ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto comment, @PathVariable Long postid) {
        CommentDto resp = commentService.createComment(comment, postid);
        return new ResponseEntity<>(resp, null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentid}")
    ResponseEntity<String> deleteComment(@PathVariable Long commentid) {
        commentService.deleteComment(commentid);
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
