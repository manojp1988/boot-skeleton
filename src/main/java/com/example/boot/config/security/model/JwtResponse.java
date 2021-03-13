package com.example.boot.config.security.model;

import com.example.boot.data.entity.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private UserData user;
}
