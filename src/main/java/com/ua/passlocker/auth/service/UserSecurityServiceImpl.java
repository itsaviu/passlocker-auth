package com.ua.passlocker.auth.service;

import com.ua.passlocker.auth.model.UserDetailsImp;
import com.ua.passlocker.auth.model.entity.Users;
import com.ua.passlocker.auth.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmailId(email).orElseThrow(() -> new RuntimeException(String.format("Invalid Email Id %s", email)));
        return new UserDetailsImp(users);
    }
}
