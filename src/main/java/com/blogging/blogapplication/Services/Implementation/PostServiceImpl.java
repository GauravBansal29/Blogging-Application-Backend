package com.blogging.blogapplication.Services.Implementation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blogging.blogapplication.Entities.Category;
import com.blogging.blogapplication.Entities.Post;
import com.blogging.blogapplication.Entities.User;
import com.blogging.blogapplication.Exceptions.ForbiddenException;
import com.blogging.blogapplication.Exceptions.ResourceNotFoundException;
import com.blogging.blogapplication.Payloads.PostDto;
import com.blogging.blogapplication.Payloads.PostPageResponse;
import com.blogging.blogapplication.Repositories.CategoryRepository;
import com.blogging.blogapplication.Repositories.PostRepository;
import com.blogging.blogapplication.Repositories.UserRepository;
import com.blogging.blogapplication.Services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Long userid, Long categoryid) {

        User finduser = userRepo.findById(userid).orElseThrow(() -> {
            return new ResourceNotFoundException("User", "id", userid);
        });

        Category findcategory = categoryRepo.findById(categoryid).orElseThrow(() -> {
            return new ResourceNotFoundException("Category", "id", categoryid);
        });

        Post createdPost = modelMapper.map(postDto, Post.class);
        createdPost.setUser(finduser);
        createdPost.setCategory(findcategory);
        createdPost.setDate(new Date());
        postRepo.save(createdPost);

        return modelMapper.map(createdPost, PostDto.class);
    }

    @Override
    public void deletePost(Long postid, Long userid) {
        Post findPost = postRepo.findById(postid).orElseThrow(() -> {
            return new ResourceNotFoundException("Post", "id", postid);
        });
        if (findPost.getUser().getId() != userid) {
            System.out.println(findPost.getUser().getId());
            throw new ForbiddenException("You can't delete this post");
        } else
            postRepo.delete(findPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postid, Long userid) {

        Post findPost = postRepo.findById(postid).orElseThrow(() -> {
            return new ResourceNotFoundException("Post", "id", postid);
        });

        if (findPost.getUser().getId() != userid)
            throw new ForbiddenException("You can update only your own posts");
        findPost.setTitle(postDto.getTitle());
        findPost.setContent(postDto.getContent());
        findPost.setImage(postDto.getImage());
        postRepo.save(findPost);

        return modelMapper.map(findPost, PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postid) {

        Post post = postRepo.findById(postid).orElseThrow(() -> {
            return new ResourceNotFoundException("Post", "id", postid);
        });

        return modelMapper.map(post, PostDto.class);

    }

    @Override
    public PostPageResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, Boolean dir) {

        Sort sort = null;
        if (dir) {
            sort = Sort.by(sortBy).ascending();
        } else
            sort = Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepo.findAll(p);
        List<Post> postlist = pagePost.getContent();
        List<PostDto> postdtoList = postlist.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostPageResponse resp = new PostPageResponse();
        resp.setPagecontent(postdtoList);
        resp.setPageNumber(pagePost.getNumber());
        resp.setPageSize(pagePost.getSize());
        resp.setTotalpages(pagePost.getTotalPages());
        resp.setTotalElements(pagePost.getTotalElements());
        resp.setIsLast(pagePost.isLast());
        return resp;
    }

    @Override
    public PostPageResponse getAllUserPosts(Long userid, Integer pageNumber, Integer pageSize, String sortBy,
            Boolean dir) {

        User finduser = userRepo.findById(userid).orElseThrow(() -> {
            return new ResourceNotFoundException("User", "id", userid);
        });
        Sort sort = null;
        if (dir) {
            sort = Sort.by(sortBy).ascending();
        } else
            sort = Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepo.findByUser(finduser, p);
        List<Post> postlist = pagePost.getContent();
        List<PostDto> postdtoList = postlist.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostPageResponse resp = new PostPageResponse();
        resp.setPagecontent(postdtoList);
        resp.setPageNumber(pagePost.getNumber());
        resp.setPageSize(pagePost.getSize());
        resp.setTotalElements(pagePost.getTotalElements());
        resp.setTotalpages(pagePost.getTotalPages());
        resp.setIsLast(pagePost.isLast());
        return resp;
    }

    @Override
    public PostPageResponse getAllCategoryPosts(Long categoryid, Integer pageNumber, Integer pageSize, String sortBy,
            Boolean dir) {

        Category findCategory = categoryRepo.findById(categoryid).orElseThrow(() -> {
            return new ResourceNotFoundException("Category", "id", categoryid);
        });
        Sort sort = null;
        if (dir) {
            sort = Sort.by(sortBy).ascending();
        } else
            sort = Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepo.findByCategory(findCategory, p);
        List<Post> postlist = pagePost.getContent();
        List<PostDto> postdtoList = postlist.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostPageResponse resp = new PostPageResponse();
        resp.setPagecontent(postdtoList);
        resp.setPageNumber(pagePost.getNumber());
        resp.setPageSize(pagePost.getSize());
        resp.setTotalElements(pagePost.getTotalElements());
        resp.setTotalpages(pagePost.getTotalPages());
        resp.setIsLast(pagePost.isLast());

        return resp;
    }

    @Override
    public PostPageResponse searchPost(String keyword, String category, int pageNumber, int pageSize, String sortBy,
            Boolean dir) {

        Sort sort = null;
        if (dir) {
            sort = Sort.by(sortBy).ascending();
        } else
            sort = Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost;
        if (category == "title")
            pagePost = postRepo.searchByTitle(keyword, p);
        else
            pagePost = postRepo.searchByContent(keyword, p);

        List<Post> postlist = pagePost.getContent();
        List<PostDto> postdtoList = postlist.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostPageResponse resp = new PostPageResponse();
        resp.setPagecontent(postdtoList);
        resp.setPageNumber(pagePost.getNumber());
        resp.setPageSize(pagePost.getSize());
        resp.setTotalElements(pagePost.getTotalElements());
        resp.setTotalpages(pagePost.getTotalPages());
        resp.setIsLast(pagePost.isLast());

        return resp;
    }

}
