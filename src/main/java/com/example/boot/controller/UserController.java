package com.example.boot.controller;

import com.example.boot.config.security.model.ResetCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/forgotPassword/{email}")
    public boolean forgotPassword(@PathVariable("email") final String email) {
        return true;
    }

    @PostMapping("/resetPassword")
    public boolean resetPassword(@RequestBody final ResetCredentials cred) {
        return true;
    }
}
