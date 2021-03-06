package com.example.boot.controller;

import com.example.boot.config.security.JWTUtil;
import com.example.boot.config.security.model.Credentials;
import com.example.boot.config.security.model.JwtResponse;
import com.example.boot.data.entity.UserData;
import com.example.boot.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/security", method = RequestMethod.GET)
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JWTUtil jwtUtil;

    @Autowired
    AuthenticationController(AuthenticationManager authenticationManager,
                             UserRepository userRepository,
                             JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Credentials credentials) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User principal = (User) authentication.getPrincipal();
        final String username = principal.getUsername();

        final UserData userData = userRepository.findByEmail(username);
        if (userData == null) {
            throw new IllegalArgumentException("Invalid user " + authentication.getPrincipal());
        }

        final String token = jwtUtil.generateToken((String) username, 30 * 60 * 1000);

        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

}
