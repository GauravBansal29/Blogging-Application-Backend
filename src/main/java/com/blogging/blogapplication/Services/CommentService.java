package com.blogging.blogapplication.Services;

import com.blogging.blogapplication.Payloads.CommentDto;
import java.util.*;

public interface CommentService {

    CommentDto createComment(CommentDto comment, Long postid, Long userid);

    void deleteComment(Long commentid, Long userid);

    List<CommentDto> getAllUserComments(Long userid);

    List<CommentDto> getAllPostComments(Long postid);
}
