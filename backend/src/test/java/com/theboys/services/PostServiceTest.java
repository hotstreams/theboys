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
        User user = new User("test", "test", UserRole.HERO);
        userRepo.save(user);

        PostTO postTO = createPost();
        Assertions.assertDoesNotThrow(() -> postService.createPost("test", postTO));
    }

    @Test
    public void testGetHeroPosts() {
        User user = new User("HeroTest", "test", UserRole.HERO);
        User savedUser = userRepo.save(user);
        PostTO post = createPost();
        postService.createPost("HeroTest", post);
        List<PostTO> heroPosts = postService.getHeroPosts(savedUser.getId(), Pageable.ofSize(10));
        Assertions.assertEquals(1, heroPosts.size());
        PostTO heroPost = heroPosts.get(0);
        Assertions.assertEquals(post.getTitle(), heroPost.getTitle());
        Assertions.assertEquals(post.getDescription(), heroPost.getDescription());
    }

    @Test
    public void testSubscriptions() {
        User user = new User("newUser", "test", UserRole.VISITOR);
        user = userRepo.save(user);

        Hero heroEntity = new Hero();
//        heroEntity.setName("NewHero");
//        heroEntity.setLogin("Y");
//        heroEntity.setPassword("test");
//        heroEntity.setRole(UserRole.HERO);
        Hero hero = heroRepo.save(heroEntity);
        Integer userId = user.getId();
//        Integer heroId = hero.getId();
//        Assertions.assertDoesNotThrow(() -> postService.subscribeToHero(userId, heroId));
//        Assertions.assertDoesNotThrow(() -> postService.unsubscribeFromHero(userId, heroId));
    }

    @Test
    public void testGetPostsBySubscription() {
        User user = new User("newUser2", "test", UserRole.VISITOR);
        user = userRepo.save(user);

        Hero hero = new Hero();
        hero.setName("X");
//        hero.setLogin("newHeroUser");
//        hero.setPassword("test");
//        hero.setRole(UserRole.HERO);
        heroRepo.save(hero);

        Integer userId = user.getId();
//        Integer heroId = hero.getId();
//        postService.subscribeToHero(userId, heroId);

        PostTO post = createPost();
        postService.createPost("newHeroUser", post);

//        List<Post> postsBySubscription = postService.getPostsBySubscription(userId, Pageable.ofSize(10));
//        Assertions.assertEquals(1, postsBySubscription.size());
//        Post heroPost = postsBySubscription.get(0);
//        Assertions.assertEquals(post.getTitle(), heroPost.getTitle());
//        Assertions.assertEquals(post.getDescription(), heroPost.getDescription());
    }

    private PostTO createPost() {
        PostTO postTO = new PostTO();
        postTO.setTitle("Title");
        postTO.setDescription("Description");
        return postTO;
    }
}
