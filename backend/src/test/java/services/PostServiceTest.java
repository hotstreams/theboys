package services;

import com.theboys.data.entities.Post;
import com.theboys.data.repos.UserRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.PostService;
import com.theboys.services.UserService;
import com.theboys.to.PostTO;
import com.theboys.to.RegistrationTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

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
        User.class
})
@Import(WebSecurityConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreatePost() {
        RegistrationTO registrationTO = new RegistrationTO();
        registrationTO.setUsername("test");
        registrationTO.setPassword("test");
        userService.register(registrationTO);

        PostTO postTO = new PostTO();
        postTO.setTitle("Title");
        postTO.setDescription("Description");
        Assertions.assertDoesNotThrow(() -> postService.createPost("test", postTO));
    }
}
