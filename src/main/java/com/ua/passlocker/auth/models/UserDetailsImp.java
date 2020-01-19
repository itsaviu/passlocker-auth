package com.ua.passlocker.auth.model;

import com.ua.passlocker.auth.model.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
@Setter
public class UserDetailsImp extends User {

    private String clientSecret;

    public UserDetailsImp(Users users) {
        super(users.getUsername(), users.getPassword(), Collections.emptyList());
        this.clientSecret = users.getClientSecret();
    }
}
