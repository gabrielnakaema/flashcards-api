package com.example.flashcardsapi.security;

import com.example.flashcardsapi.exception.FlashcardsApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setStatus(403);
            ObjectMapper mapper = new ObjectMapper();
            httpServletResponse.getWriter().write(mapper.writeValueAsString(new FlashcardsApiException(HttpStatus.FORBIDDEN,"Authentication is required to access this resource")));
    }
}
