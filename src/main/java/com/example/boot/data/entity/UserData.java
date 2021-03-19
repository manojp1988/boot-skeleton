package com.example.boot.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity(name = "User")
@Table(name = "Users")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @JsonIgnore
    private String password;
    private String name;

    @Column
    private boolean passwordExpired;

    @Transient
    private String token;

    public String[] getAuthorities() {
        return new String[] {
                "ADMIN"
        };
    }

    @JsonIgnore
    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        return Arrays.stream(getAuthorities())
                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toList());
    }
}
