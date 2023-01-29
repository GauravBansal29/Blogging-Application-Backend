package com.blogging.blogapplication.Payloads;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blogging.blogapplication.Entities.Category;
import com.blogging.blogapplication.Entities.Comment;
import com.blogging.blogapplication.Entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    @Size(min = 5, message = "The size should be atleast 5")
    private String title;

    @NotEmpty(message = "The post should not be empty")
    private String content;

    // when we will receive it will be empty
    private Date date;

    // when we will receive it may be empty
    private String image;

    // we will not get but will have to return this
    private UserDto user;

    // we will not get but will have to return this
    private CategoryDto category;

    private List<CommentDto> comments;

}
