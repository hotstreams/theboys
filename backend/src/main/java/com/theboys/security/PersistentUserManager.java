package com.theboys.security;

import com.theboys.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

@Service
public class PersistentUserManager implements UserDetailsService {

    UserRepo userRepo;

    @Autowired
    public PersistentUserManager(UserRepo userRepo) {
        this.userRepo = userRepo;
        var pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userRepo.save(new User(1, "login", pe.encode("password"), UserRole.VISITOR));
        userRepo.save(new User(2, "science", pe.encode("password"), UserRole.SCIENTIST));
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
}
