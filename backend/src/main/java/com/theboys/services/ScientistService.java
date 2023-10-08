package com.theboys.services;

import com.theboys.data.repos.ScientistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScientistService {

    private final ScientistRepo scientistRepo;
    private final UserService userService;

    @Autowired
    public ScientistService(ScientistRepo scientistRepo,
                            UserService userService) {
        this.scientistRepo = scientistRepo;
        this.userService = userService;
    }
}
