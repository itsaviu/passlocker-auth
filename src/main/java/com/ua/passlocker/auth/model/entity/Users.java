package com.ua.passlocker.auth.model.entity;

import com.ua.passlocker.auth.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String emailId;

    private String clientSecret;

    private Boolean confirmed;

    private Boolean active;


    public Users(String username, String emailId, String password, Boolean confirmed, Boolean active) {
        this.username = username;
        this.password = SecurityUtils.encryptDate(password);
        this.emailId = emailId;
        this.confirmed = confirmed;
        this.clientSecret = SecurityUtils.uniqueHashCode();
        this.active = active;
    }

}
