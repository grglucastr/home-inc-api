package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.repositories.UserRepository;
import com.grglucastr.homeincapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
