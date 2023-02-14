package com.blogging.blogapplication.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogging.blogapplication.Entities.User;
import com.blogging.blogapplication.Exceptions.ForbiddenException;
import com.blogging.blogapplication.Exceptions.NotFoundException;
import com.blogging.blogapplication.Repositories.UserRepository;

@Component
public class UserOnlyRequest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    public void allow(String token, Long id) {
        token = token.substring(7); // removing Bearer from token
        String username = this.jwtTokenHelper.getUsernameFromToken(token);
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> {
            return new NotFoundException("Invalid email or password");
        });
        if (user.getId() != id)
            throw new ForbiddenException("Forbidden access : Cannot access this route");
    }
}
