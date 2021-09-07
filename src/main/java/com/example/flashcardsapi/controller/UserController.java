package com.example.flashcardsapi.controller;

import com.example.flashcardsapi.payload.UserDetailsResponse;
import com.example.flashcardsapi.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDetailsResponse getLoggedInUserDetails(){
        return userService.getLoggedInUserDetails();
    }
}
