package com.blogging.blogapplication.Services;

import com.blogging.blogapplication.Payloads.CommentDto;
import java.util.*;

public interface CommentService {

    CommentDto createComment(CommentDto comment, Long postid);

    void deleteComment(Long commentid);

    List<CommentDto> getAllUserComments(Long userid);

    List<CommentDto> getAllPostComments(Long postid);
}
