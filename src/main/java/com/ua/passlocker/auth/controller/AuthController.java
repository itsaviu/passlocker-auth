package com.ua.passlocker.auth.controller;


import com.ua.passlocker.auth.models.dto.RegisterUserReq;
import com.ua.passlocker.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterUserReq registerUserReq) {
        userService.register(registerUserReq);
        return ResponseEntity.ok().build();
    }

}
