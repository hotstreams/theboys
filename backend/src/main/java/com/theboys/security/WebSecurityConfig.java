package com.theboys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.theboys.security.UserRole.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private PersistentUserManager userManager;

    //todo Remade it
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/users").anonymous()
                        .requestMatchers("/heroes/rent").hasAnyRole(CUSTOMER.name())
                        .requestMatchers("/heroes/rents").hasAnyRole(HERO.name())
                        .requestMatchers("/heroes/*/rates").authenticated()
                        .requestMatchers("/heroes").permitAll()
                        .requestMatchers("/entrepreneurs/**").hasAnyRole(CUSTOMER.name(), MANAGER.name())
                        .requestMatchers("/researches").hasRole(SCIENTIST.name())
                        .requestMatchers("/posts/subscriptions/**").authenticated()
                        .requestMatchers("/posts/subscriptions").authenticated()
                        .requestMatchers("/posts").hasRole(HERO.name())
                        .requestMatchers("/candidates").hasRole(MANAGER.name())
                        .requestMatchers("/medicines").hasRole(SCIENTIST.name())
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(c -> c.authenticationEntryPoint(basicAuthenticationEntryPoint())
                        .securityContextRepository(new NullSecurityContextRepository())
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .userDetailsService(userManager)
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint() {
        var result = new BasicAuthenticationEntryPoint();
        result.setRealmName("TEST REALM");
        return result;
    }

}
