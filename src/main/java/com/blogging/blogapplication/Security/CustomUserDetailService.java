package com.blogging.blogapplication.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogging.blogapplication.Entities.User;
import com.blogging.blogapplication.Exceptions.NotFoundException;
import com.blogging.blogapplication.Repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by username
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> {
            return new NotFoundException("Invalid email or password");
        });

        return user;

    }

}
