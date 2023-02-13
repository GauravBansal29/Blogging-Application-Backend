package com.blogging.blogapplication.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import com.blogging.blogapplication.Exceptions.NotFoundException;
import com.blogging.blogapplication.Exceptions.ResourceNotFoundException;
import com.blogging.blogapplication.Payloads.JwtAuthReqDto;
import com.blogging.blogapplication.Payloads.JwtAuthResDto;
import com.blogging.blogapplication.Security.JwtTokenHelper;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResDto> loginUser(@RequestBody JwtAuthReqDto req) throws Exception {
        this.authenticate(req.getUsername(), req.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(req.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResDto response = new JwtAuthResDto();
        response.setToken(token);
        return new ResponseEntity<JwtAuthResDto>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {

            System.out.println("Invalid  details");
            throw new NotFoundException("Invalid username or password");

        }
    }
}
