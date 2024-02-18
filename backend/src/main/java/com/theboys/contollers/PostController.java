package com.theboys.contollers;

import com.theboys.data.entities.Post;
import com.theboys.security.PersistentUserManager;
import com.theboys.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;
    private final PersistentUserManager userService;

    @Autowired
    public PostController(PostService postService, PersistentUserManager userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping(path = "/subscriptions")
    public List<Post> getBySubscriptions(Principal principal, Pageable pageable) {
        int userId = userService.loadUserIdByUsername(principal.getName());
        System.out.println("User id: " + userId);
        return postService.getPostsBySubscription(userId, pageable);
    }

    @GetMapping(path = "/hero/{heroId}")
    public List<Post> getHeroPosts(@PathVariable(value = "heroId") int heroId, Pageable pageable) {
        return postService.getHeroPosts(heroId, pageable);
    }
}
