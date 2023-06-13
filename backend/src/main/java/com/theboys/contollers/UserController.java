package com.theboys.contollers;

import com.theboys.services.UserService;
import com.theboys.to.CustomHttpResponse;
import com.theboys.to.LoginResponseTO;
import com.theboys.to.RegistrationTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
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

    @PostMapping
    public CustomHttpResponse register(@RequestBody RegistrationTO registrationTO) {
        userService.register(registrationTO);
        return new CustomHttpResponse();
    }
}
