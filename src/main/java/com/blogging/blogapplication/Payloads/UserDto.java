package com.blogging.blogapplication.Payloads;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// tells which data can be exposed in API's -> data other than the self generated data by us
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;

    @Size(min = 3, message = "Username must be of size atleast 3")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 5, max = 20, message = "Password must be of min 5 and max 20 characters")
    private String password;

    @NotEmpty(message = "About section should not be empty")
    private String about;

    private List<CommentDto> comments;

}
