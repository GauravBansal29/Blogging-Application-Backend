package com.blogging.blogapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogging.blogapplication.Entities.Comment;
import com.blogging.blogapplication.Entities.User;
import com.blogging.blogapplication.Entities.Post;
import java.util.*;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUser(User user);

    List<Comment> findByPost(Post post);
}
