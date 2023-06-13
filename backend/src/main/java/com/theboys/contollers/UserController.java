package com.theboys.contollers;

import com.theboys.services.UserService;
import com.theboys.to.LoginResponseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{username}")
    public LoginResponseTO login(@PathVariable("username") String username) {
        return userService.login(username);
    }
}
