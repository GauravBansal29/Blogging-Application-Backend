package com.blogging.blogapplication.Security;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// authentication entry point from spring security dependency

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.core.AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token not found");

    }
}
