package com.theboys.services;

import com.theboys.data.entities.Hero;
import com.theboys.data.entities.Post;
import com.theboys.data.repos.HeroRepo;
import com.theboys.data.repos.UserRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.UserRole;
import com.theboys.security.WebSecurityConfig;
import com.theboys.to.PostTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootTest(
        classes = {
                PersistentUserManager.class,
                PostService.class,
                UserService.class
        })
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = UserRepo.class)
@EntityScan(basePackageClasses = {
        Post.class,
        User.class,
        Hero.class
})
@Import(WebSecurityConfig.class)
public class PostServiceTest {

    @Autowired
    private HeroRepo heroRepo;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void testCreatePost() {
        PostTO postTO = createPost();
        Assertions.assertDoesNotThrow(() -> postService.createPost("hero1", postTO));
    }

    @Test
    public void testGetHeroPosts() {
        List<PostTO> heroPosts = postService.getHeroPosts(7, Pageable.ofSize(10));
        Assertions.assertFalse(heroPosts.isEmpty());
    }

    @Test
    public void testSubscriptions() {
        Assertions.assertDoesNotThrow(() -> postService.subscribeToHero(2, 8));
        Assertions.assertDoesNotThrow(() -> postService.unsubscribeFromHero(2, 8));
    }

    @Test
    public void testGetPostsBySubscription() {
        List<PostTO> postsBySubscription = postService.getPostsBySubscription(1, Pageable.ofSize(10));
        Assertions.assertFalse(postsBySubscription.isEmpty());
    }

    private PostTO createPost() {
        PostTO postTO = new PostTO();
        postTO.setTitle("Title");
        postTO.setDescription("Description");
        return postTO;
    }
}
