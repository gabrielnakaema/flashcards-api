package com.example.flashcardsapi.service;

import com.example.flashcardsapi.model.User;
import com.example.flashcardsapi.payload.NewUserRequest;
import com.example.flashcardsapi.repository.UserRepository;
import com.example.flashcardsapi.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(NewUserRequest newUser) {
        if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "confirm password does not match password");
        }
        if (!checkIfUsernameIsAvailable(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "username is already taken");
        }

        User newUserEntity = new User();
        newUserEntity.setUsername(newUser.getUsername());
        newUserEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUserEntity.setName(newUser.getName());

        return userRepository.save(newUserEntity);
    }

    public Boolean checkIfUsernameIsAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));

        return UserPrincipal.builder()
                .id(foundUser.getId())
                .name(foundUser.getName())
                .username(foundUser.getUsername())
                .password(foundUser.getPassword())
                .build();
    }
}
