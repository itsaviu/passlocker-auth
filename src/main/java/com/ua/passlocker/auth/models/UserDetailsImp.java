package com.ua.passlocker.auth.models;

import com.ua.passlocker.auth.models.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
@Setter
public class UserDetailsImp extends User {

    private String clientSecret;

    private String emailId;

    public UserDetailsImp(Users users) {
        super(users.getUsername(), users.getPassword(), Collections.emptyList());
        this.clientSecret = users.getClientSecret();
        this.emailId = users.getEmailId();
    }
}
