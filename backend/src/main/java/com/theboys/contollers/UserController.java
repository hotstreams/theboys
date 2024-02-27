package com.theboys.contollers;

import com.theboys.exceptions.UserNotFoundException;
import com.theboys.services.UserService;
import com.theboys.to.CustomHttpResponse;
import com.theboys.to.LoginResponseTO;
import com.theboys.to.RegistrationTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{username}")
    public LoginResponseTO login(@PathVariable("username") String username) throws UserNotFoundException {
        return userService.login(username);
    }

    @PostMapping
    public CustomHttpResponse register(@Valid @RequestBody RegistrationTO registrationTO) {
        userService.register(registrationTO);
        return new CustomHttpResponse();
    }
}
