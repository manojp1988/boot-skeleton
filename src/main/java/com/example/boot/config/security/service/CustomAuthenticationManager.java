package com.example.boot.config.security.service;

import com.example.boot.config.security.PasswordUtil;
import com.example.boot.data.entity.UserData;
import com.example.boot.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private UserRepository userRepository;

    @Autowired
    CustomAuthenticationManager(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String principal = authentication.getName();
        final String credentials = authentication.getCredentials().toString();

        final UserData userData = userRepository.findByEmail( principal);

        if(userData == null || !userData.getPassword().equals(PasswordUtil.encrypt(credentials)))
            throw new BadCredentialsException("Either username or password is invalid");

        if(userData.isPasswordExpired()) {
            throw new CredentialsExpiredException("Password is expired");
        }

        UsernamePasswordAuthenticationToken unameAuthentication = new UsernamePasswordAuthenticationToken(
                userData.getEmail(), "", userData.getGrantedAuthorities());
        return unameAuthentication;
    }
}
