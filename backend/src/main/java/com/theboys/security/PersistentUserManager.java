package com.theboys.security;

import com.theboys.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersistentUserManager implements UserDetailsService {

    private final UserRepo userRepo;


    @Autowired
    public PersistentUserManager(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unable to find user with username '" + username + "'");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }

    public int loadUserIdByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unable to find user with username '" + username + "'");
        }

        return user.getId();
    }
}
