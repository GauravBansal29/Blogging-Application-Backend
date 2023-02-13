package com.blogging.blogapplication.Payloads;

import lombok.Data;

@Data
public class JwtAuthReqDto {

    private String username;
    private String password;
}
