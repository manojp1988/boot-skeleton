package com.example.boot.service;

import com.example.boot.config.security.PasswordUtil;
import com.example.boot.data.entity.UserData;
import com.example.boot.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {


    private UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository){

        this.userRepository = userRepository;
    }

    public boolean updateNewPassword(final String username) {
        final UserData userData = userRepository.findByEmail(username);
        if(userData == null) return false;

        final String newPassword = PasswordUtil.generatePassword();
        userData.setPassword(newPassword);
        userData.setPasswordExpired(true);
        userRepository.save(userData);

        return true;
    }
}
