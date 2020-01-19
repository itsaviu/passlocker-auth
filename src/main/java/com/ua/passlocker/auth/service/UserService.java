package com.ua.passlocker.auth.service;

import com.ua.passlocker.auth.models.dto.RegisterUserReq;
import com.ua.passlocker.auth.models.dto.UserDetailReq;
import com.ua.passlocker.auth.models.entity.Users;
import com.ua.passlocker.auth.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public void register(RegisterUserReq registerUserReq) {
        usersRepository.save(new Users(registerUserReq.getUsername(), registerUserReq.getEmailId(), registerUserReq.getPassword(), false, true));
    }

    public List<Users> fetchUsers(UserDetailReq userDetailReq) {
        if (userDetailReq.getFullSync())
            return usersRepository.findAll();

        return usersRepository.findAllById(userDetailReq.getUserIds());
    }

}
