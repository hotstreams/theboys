package com.theboys.controllers;

import com.theboys.contollers.HeroController;
import com.theboys.exceptions.RestResponseEntityExceptionHandler;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.HeroService;
import com.theboys.services.OrderService;
import com.theboys.to.HeroTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import com.theboys.SecurityTestConfigurationHelper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.theboys.SecurityTestConfigurationHelper.AUTHORIZATION;
import static com.theboys.SecurityTestConfigurationHelper.BASE64HEADER;

@WebMvcTest
@ContextConfiguration(classes = {HeroController.class, WebSecurityConfig.class, RestResponseEntityExceptionHandler.class})
@TestPropertySource(locations = {"/application.properties"})
@AutoConfigureMockMvc
@Import(SecurityTestConfigurationHelper.class)
public class HeroControllerTest {

    @Autowired
    private PersistentUserManager userService;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeroService heroService;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setupUserService() {

    }

    @WithAnonymousUser
    @Test
    public void testGetHeroesUnauthorized() throws Exception {
        List<HeroTO> heroes = new ArrayList<>();
        when(heroService.getHeroes()).thenReturn(heroes);
        mockMvc.perform(get("/heroes")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json("[]"));
    }

    @WithMockUser
    @Test
    public void testGetHeroes() throws Exception {
        List<HeroTO> heroes = new ArrayList<>();
        when(heroService.getHeroes()).thenReturn(heroes);
        mockMvc.perform(get("/heroes").header(AUTHORIZATION, BASE64HEADER)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json("[]"));
        heroes.add(new HeroTO(2, "Sting", 4.4d, "cringe", "the most cringy hero"));
        mockMvc.perform(get("/heroes").header(AUTHORIZATION, BASE64HEADER)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json("""
                        [{
                            "id":2,
                            "name":"Sting",
                            "rating":4.4,
                            "abilities":"cringe",
                            "description":"the most cringy hero"
                        }]
                        """));
        heroes.add(new HeroTO(3, "Sheesh", 4.5d, "none", "he paid to become a 'hero'"));
        mockMvc.perform(get("/heroes").header(AUTHORIZATION, BASE64HEADER)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json("""
                        [{
                            "id":2,
                            "name":"Sting",
                            "rating":4.4,
                            "abilities":"cringe",
                            "description":"the most cringy hero"
                        },{
                            "id":3,
                            "name":"Sheesh",
                            "rating":4.5,
                            "abilities":"none",
                            "description":"he paid to become a 'hero'"
                        }]
                        """));
    }

    @WithMockUser(roles = {"CUSTOMER"})
    @Test
    public void testRentHero() throws Exception {
        mockMvc.perform(post("/heroes/rent").header(AUTHORIZATION, BASE64HEADER).contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                        "heroDescription":"Something useless please",
                        "dateStart":"2024-01-01T16:00:00",
                        "dateEnd":"2024-01-02T16:00:00"
                        }
                        """
                )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
            """
                        {"status":"OK"}
                       """
                ));
    }

    @WithMockUser(roles = {"HERO", "MANAGER", "SCIENTIST", "USER", "CANDIDATE"})
    @Test
    public void testRentHeroForbidden() throws Exception {
        mockMvc.perform(post("/heroes/rent").header(AUTHORIZATION, BASE64HEADER).contentType(MediaType.APPLICATION_JSON).content(
                        """
                        {
                        "heroDescription":"Something useless please",
                        "dateStart":"2024-01-01T16:00:00",
                        "dateEnd":"2024-01-02T16:00:00"
                        }
                        """
                )).andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string(""));
    }

    @WithMockUser(roles = {"HERO"})
    @Test
    public void testGetHeroRents() throws Exception {
        mockMvc.perform(get("/heroes/rents").header(AUTHORIZATION, BASE64HEADER)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @WithMockUser(roles = {"CUSTOMER", "MANAGER", "SCIENTIST", "USER", "CANDIDATE"})
    @Test
    public void testGetHeroRentsForbidden() throws Exception {
        mockMvc.perform(get("/heroes/rents").header(AUTHORIZATION, BASE64HEADER)).andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string(""));
    }

    @WithMockUser(roles = {"USER"})
    @Test
    public void testRateHero() throws Exception {
        mockMvc.perform(post("/heroes/1/rate?rate=5").header(AUTHORIZATION, BASE64HEADER)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        mockMvc.perform(post("/heroes/1/rate?rate=6").header(AUTHORIZATION, BASE64HEADER)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("rateHero.rate must be less than or equal to 5"));
    }

}