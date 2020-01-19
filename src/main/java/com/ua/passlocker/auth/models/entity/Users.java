package com.ua.passlocker.auth.models.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.ua.passlocker.auth.utils.SecurityUtils;
import com.ua.passlocker.auth.views.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.UserViews.class})
    private Long id;

    @JsonView({Views.UserViews.class})
    private String username;

    private String password;

    @JsonView({Views.UserViews.class})
    private String emailId;

    private String clientSecret;

    private Boolean confirmed;

    private Boolean active;

    private Timestamp createdAt;


    public Users(String username, String emailId, String password, Boolean confirmed, Boolean active) {
        this.username = username;
        this.password = SecurityUtils.encryptDate(password);
        this.emailId = emailId;
        this.confirmed = confirmed;
        this.clientSecret = SecurityUtils.uniqueHashCode();
        this.active = active;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

}
