package com.blogging.blogapplication.Payloads;

import javax.validation.constraints.*;

import com.blogging.blogapplication.Entities.Post;
import com.blogging.blogapplication.Entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    @Size(min = 10, max = 200)
    private String content;

}
