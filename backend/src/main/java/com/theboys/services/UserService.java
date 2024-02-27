package com.theboys.services;

import com.theboys.data.repos.UserRepo;
import com.theboys.exceptions.UserNotFoundException;
import com.theboys.security.User;
import com.theboys.security.UserRole;
import com.theboys.to.LoginResponseTO;
import com.theboys.to.RegistrationTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public void updateUserRole(UserRole role, Integer id) {
        userRepo.updateUpdateUserRole(role, id);
    }

    public LoginResponseTO login(String username) throws UserNotFoundException {
        User user = getUserByLogin(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return new LoginResponseTO(user.getId(), user.getLogin(), user.getRole(), null);
    }

    public void register(RegistrationTO registrationTO) {
        String password = passwordEncoder.encode(registrationTO.getPassword());
        User user = new User(registrationTO.getUsername(), password, UserRole.VISITOR);
        userRepo.save(user);
    }
}
