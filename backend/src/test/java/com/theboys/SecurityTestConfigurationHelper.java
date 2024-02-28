package com.theboys;

import com.theboys.security.PersistentUserManager;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.User;

import static org.mockito.Mockito.when;

@TestConfiguration
public class SecurityTestConfigurationHelper {
    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";

    public static final String BASE64HEADER = "dXNlcjpwYXNzd29yZA==";

    public static final String AUTHORIZATION = HttpHeaders.AUTHORIZATION;

    @Bean
    @Primary
    public PersistentUserManager persistentUserManager() {
        System.out.println("TAAAAG");
        PersistentUserManager mock = Mockito.mock(PersistentUserManager.class);
        when(mock.loadUserIdByUsername(USERNAME)).thenReturn(1);
        when(mock.loadUserByUsername(USERNAME)).thenReturn(User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .accountExpired(false)
                .accountLocked(false)
                .roles("USER")
                .credentialsExpired(false)
                .disabled(false)
                .build());
        return mock;
    }
}
