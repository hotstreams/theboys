package com.theboys.services;

import com.theboys.data.repos.UserRepo;
import com.theboys.security.User;
import com.theboys.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public void updateUserRole(UserRole role, Integer id) {
        userRepo.updateUpdateUserRole(role, id);
    }
}
