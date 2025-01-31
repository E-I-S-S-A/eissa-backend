package com.eissa.backend.accounts.services;

import com.eissa.backend.accounts.models.classes.entities.User;
import com.eissa.backend.accounts.repos.UserRepo;
import com.eissa.backend.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public String getUserIdFromAccessToken(String accessToken) {
        String userId = JwtUtil.validateToken(accessToken);
        return userId;
    }

    public User getUser(String userId) {
        User userFromDb = userRepo.getUserFromEmailOrId(userId);
        return userFromDb;
    }
}
