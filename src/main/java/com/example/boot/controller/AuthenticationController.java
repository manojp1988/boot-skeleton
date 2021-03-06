package com.example.boot.controller;

import com.example.boot.config.security.JWTUtil;
import com.example.boot.config.security.model.Credentials;
import com.example.boot.config.security.model.ResetCredentials;
import com.example.boot.data.entity.UserData;
import com.example.boot.data.repository.UserRepository;
import com.example.boot.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/security")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private AuthenticationService authenticationService;
    private JWTUtil jwtUtil;

    @Autowired
    AuthenticationController(AuthenticationManager authenticationManager,
                             UserRepository userRepository,
                             AuthenticationService authenticationService,
                             JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserData> login(@RequestBody Credentials credentials) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String username = (String) authentication.getPrincipal();

        final UserData userData = userRepository.findByEmail(username);
        if (userData == null) {
            throw new IllegalArgumentException("Invalid user " + authentication.getPrincipal());
        }

        final String token = jwtUtil.generateToken(username, 30 * 60 * 1000);
        userData.setToken(token);

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @GetMapping("/forgotPassword/{email}")
    public boolean forgotPassword(@PathVariable("email") final String email) {
        return authenticationService.updateNewPassword(email);
    }

    @PostMapping("/resetPassword")
    public boolean resetPassword(@RequestBody final ResetCredentials cred) {
        return true;
    }
}
