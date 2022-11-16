package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findById(Long userId);

}
