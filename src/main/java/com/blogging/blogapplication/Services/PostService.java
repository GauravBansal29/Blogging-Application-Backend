package com.blogging.blogapplication.Services;

import java.util.List;
import com.blogging.blogapplication.Payloads.PostDto;
import com.blogging.blogapplication.Payloads.PostPageResponse;

public interface PostService {

    // functions of the postservice

    // add post
    PostDto createPost(PostDto postDto, Long userid, Long categoryid);

    // delete post
    void deletePost(Long postid, Long userid);

    // update post
    PostDto updatePost(PostDto postDto, Long postid, Long userid);

    // get post by id
    PostDto getPostById(Long postid);

    // get all posts
    PostPageResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, Boolean dir);

    // fetch all posts of a user -> user posts page
    PostPageResponse getAllUserPosts(Long userid, Integer pageNumber, Integer pageSize, String sortBy, Boolean dir);

    // fetch all posts of a category -> for showing a category blog page
    PostPageResponse getAllCategoryPosts(Long postid, Integer pageNumber, Integer pageSize, String sortBy, Boolean dir);

    // search posts
    PostPageResponse searchPost(String keyword, String category, int pageNumber, int pageSize, String sortBy,
            Boolean dir);

}
