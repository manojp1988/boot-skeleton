package com.example.boot.config.security.model;

import lombok.Data;

@Data
public class ResetCredentials {
    private String oldPassword;
    private String newPassword;
}
