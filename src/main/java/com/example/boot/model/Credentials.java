package com.example.boot.model;

import lombok.Data;

@Data
public class Credentials {
    private String oldPassword;
    private String newPassword;
}
