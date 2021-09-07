package com.example.flashcardsapi.controller;

import com.example.flashcardsapi.model.User;
import com.example.flashcardsapi.payload.AuthenticationRequest;
import com.example.flashcardsapi.payload.AuthenticationResponse;
import com.example.flashcardsapi.payload.FlashcardsApiResponse;
import com.example.flashcardsapi.payload.NewUserRequest;
import com.example.flashcardsapi.security.JwtTokenUtil;
import com.example.flashcardsapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
            AuthenticationResponse authResponse = new AuthenticationResponse();
            authResponse.setToken(jwtTokenUtil.generateToken(userDetails));
            authResponse.setUsername(userDetails.getUsername());
            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new FlashcardsApiResponse(e.getMessage(), 401));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody @Valid NewUserRequest newUserRequest) {
        return ResponseEntity.ok(userService.createUser(newUserRequest));
    }

}
