package com.ua.passlocker.auth.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.ua.passlocker.auth.models.dto.UserDetailReq;
import com.ua.passlocker.auth.service.UserService;
import com.ua.passlocker.auth.views.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @JsonView({Views.UserViews.class})
    public ResponseEntity fetchUsers(@RequestBody UserDetailReq userDetailReq) {
        return ResponseEntity.ok(userService.fetchUsers(userDetailReq));
    }
}
