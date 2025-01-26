package com.example.authtodo.service;

import com.example.authtodo.entity.JwtResponse;
import com.example.authtodo.entity.User;

public interface UserService {

    User registerUser(String username, String password);

    JwtResponse login(String username, String password);
    JwtResponse refresh(String refreshToken);
}
