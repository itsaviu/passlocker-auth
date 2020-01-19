package com.ua.passlocker.auth.service;

import com.ua.passlocker.auth.model.dto.RegisterUserReq;
import com.ua.passlocker.auth.model.entity.Users;
import com.ua.passlocker.auth.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public void register(RegisterUserReq registerUserReq) {
        usersRepository.save(new Users(registerUserReq.getUsername(), registerUserReq.getEmailId(), registerUserReq.getPassword(), false, true));
    }

}
