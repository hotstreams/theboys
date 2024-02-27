package com.theboys.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theboys.SecurityTestConfigurationHelper;
import com.theboys.contollers.UserController;
import com.theboys.exceptions.RestResponseEntityExceptionHandler;
import com.theboys.exceptions.UserNotFoundException;
import com.theboys.security.UserRole;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.UserService;
import com.theboys.to.LoginResponseTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {UserController.class, WebSecurityConfig.class, RestResponseEntityExceptionHandler.class})
@TestPropertySource(locations = {"/application.properties"})
@AutoConfigureMockMvc
@Import(SecurityTestConfigurationHelper.class)
public class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @WithAnonymousUser
    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                            "username":"cringe",
                            "password":"moreCringe"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        """
                        {
                            "status":"OK"
                        }
                        """));
    }

    @WithAnonymousUser
    @Test
    public void testRegisterInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                            "username":"",
                            "password":""
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = {"HERO", "SCIENTIST", "MANAGER", "USER", "CANDIDATE", "CUSTOMER"})
    @Test
    public void testRegisterForbidden() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                            "username":"cringe",
                            "password":"moreCringe"
                        }
                        """))
                .andExpect(status().isForbidden());
    }

    @WithAnonymousUser
    @Test
    public void login() throws Exception {
        String username = "correctUsername";
        Integer id = 1;
        UserRole userRole = UserRole.CANDIDATE;

        LoginResponseTO loginResponseTO = new LoginResponseTO();
        loginResponseTO.setUsername(username);
        loginResponseTO.setRole(userRole);
        loginResponseTO.setId(id);


        Mockito.when(userService.login(username))
                        .thenReturn(loginResponseTO);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/" + username))
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    int status = response.getStatus();
                    String contentAsString = response.getContentAsString();

                    LoginResponseTO responseTO = objectMapper.readValue(contentAsString, LoginResponseTO.class);
                    Assertions.assertEquals(200, status);
                    Assertions.assertEquals(loginResponseTO.getUsername(),responseTO.getUsername());
                    Assertions.assertEquals(loginResponseTO.getRole(), responseTO.getRole());
                    Assertions.assertEquals(loginResponseTO.getId(), responseTO.getId());
                });
    }

    @WithAnonymousUser
    @Test
    public void loginException() throws Exception {
        Mockito.when(userService.login(Mockito.anyString()))
                .thenThrow(UserNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/" + "test"))
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    int status = response.getStatus();
                    Assertions.assertEquals(404, status);
                });
    }

}