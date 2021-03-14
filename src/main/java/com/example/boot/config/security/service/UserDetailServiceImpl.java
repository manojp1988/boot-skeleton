package com.example.boot.config.security.service;

import com.example.boot.data.entity.UserData;
import com.example.boot.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        final UserData customUser = userRepository.findByEmail(username);

        return User.builder()
                   .username(customUser.getEmail())
                   .password(encoder.encode(customUser.getPassword()))
                   .roles(customUser.getAuthorities())
                   .build();
    }
}
