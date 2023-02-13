package com.blogging.blogapplication.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }
}
