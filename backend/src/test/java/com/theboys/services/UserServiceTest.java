package com.theboys.services;

import com.theboys.data.entities.Hero;
import com.theboys.data.repos.UserRepo;
import com.theboys.exceptions.UserNotFoundException;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.UserRole;
import com.theboys.security.WebSecurityConfig;
import com.theboys.to.LoginResponseTO;
import com.theboys.to.RegistrationTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest(
        classes = {
                PersistentUserManager.class,
                UserService.class
        })
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = UserRepo.class)
@EntityScan(basePackageClasses = {
        User.class,
        Hero.class
})
@Import(WebSecurityConfig.class)
public class UserServiceTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;
    @Test
    public void testRegister() {
        RegistrationTO registrationTO = new RegistrationTO("test3", "test");
        Assertions.assertDoesNotThrow(() -> userService.register(registrationTO));
    }

    @Test
    public void testUserByLogin() {
        RegistrationTO registrationTO = new RegistrationTO("test2", "test");
        userService.register(registrationTO);
        User test = userService.getUserByLogin("test2");
        Assertions.assertNotNull(test);
    }

    @Test
    public void testLogin() throws UserNotFoundException {
        RegistrationTO registrationTO = new RegistrationTO("testUser", "test");
        userService.register(registrationTO);
        User test = userService.getUserByLogin("testUser");
        userService.updateUserRole(UserRole.HERO, test.getId());
        LoginResponseTO testUser = userService.login("testUser");
        Assertions.assertEquals(UserRole.HERO, testUser.getRole());
        Assertions.assertEquals("testUser", testUser.getUsername());
    }

    @Test
    public void testLoginFailed() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.login("testUser2"));
    }

    @Test
    public void testUpdateUserRole() {
        RegistrationTO registrationTO = new RegistrationTO("test", "test");
        userService.register(registrationTO);
        User test = userService.getUserByLogin("test");
        UserRole userRole = UserRole.CANDIDATE;
        userService.updateUserRole(userRole, test.getId());
        test = userService.getUserByLogin("test");

        Assertions.assertEquals(userRole, test.getRole());
    }
}
