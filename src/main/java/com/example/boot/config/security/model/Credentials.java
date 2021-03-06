package com.example.boot.config.security.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class Credentials implements Serializable {

    private String username;
    private String password;

}
