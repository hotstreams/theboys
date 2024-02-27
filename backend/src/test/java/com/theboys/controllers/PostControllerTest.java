package com.theboys.controllers;

import com.theboys.contollers.PostController;
import com.theboys.data.entities.Post;
import com.theboys.exceptions.RestResponseEntityExceptionHandler;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.PostService;
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

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {PostController.class, WebSecurityConfig.class, RestResponseEntityExceptionHandler.class})
@TestPropertySource(locations = {"/application.properties"})
@AutoConfigureMockMvc
@Import(SecurityTestConfigurationHelper.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private PersistentUserManager userManager;

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testGetPosts() throws Exception {
        ArrayList<Post> posts = new ArrayList<>();
        when(postService.getPostsBySubscription(anyInt(), any())).thenReturn(posts);
        mockMvc.perform(get("/posts/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        posts.add(new Post(2, 7, "Hero saves people", "Nothing unusual", OffsetDateTime.of(2020, 1, 2, 4, 2,30, 0, ZoneOffset.UTC)));
        mockMvc.perform(get("/posts/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [{
                            "postId": 2,
                            "heroId":7,
                            "title":"Hero saves people",
                            "description":"Nothing unusual",
                            "at":"2020-01-02T04:02:30Z"
                        }]
                        """));

    }

    @WithAnonymousUser
    @Test
    public void testGetPostsUnauthorized() throws Exception {
        mockMvc.perform(get("/posts/subscriptions"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    public void testSubscribeToHero() throws Exception {
        mockMvc.perform(post("/posts/subscriptions/1"))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void testSubscribeToHeroUnauthorized() throws Exception {
        mockMvc.perform(post("/posts/subscriptions/1"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    public void testUnsubscribeFromHero() throws Exception {
        mockMvc.perform(delete("/posts/subscriptions/1"))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void testUnsubscribeFromHeroUnauthorized() throws Exception {
        mockMvc.perform(delete("/posts/subscriptions/1"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = {"HERO"})
    @Test
    public void testCreatePost() throws Exception {
        mockMvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON).content(
                        """
                            {
                                "title":"Saving the city in a third time",
                                "description":"Bruh, I don't want to think"
                            }
                        """))
                .andExpect(status().isOk());

    }

    @WithMockUser(roles = {"HERO"})
    @Test
    public void testCreatePostInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON).content(
                        """
                            {
                                "title":"",
                                "description":"This is a post with no title"
                            }
                        """))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = {"SCIENTIST", "MANAGER", "CANDIDATE", "CUSTOMER"})
    @Test
    public void testCreatePostForbidden() throws Exception {
        mockMvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON).content(
                        """
                            {
                                "title":"Saving the city in a third time",
                                "description":"Bruh, I don't want to think"
                            }
                        """))
                .andExpect(status().isForbidden());

    }

}