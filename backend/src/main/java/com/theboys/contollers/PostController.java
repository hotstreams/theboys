package com.theboys.contollers;

import com.theboys.data.entities.Post;
import com.theboys.security.PersistentUserManager;
import com.theboys.services.PostService;
import com.theboys.to.PostTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
        return postService.getPostsBySubscription(userId, pageable);
    }

    @PostMapping(path = "/subscriptions/{heroId}")
    public void subscribeToHero(Principal principal, @PathVariable(value = "heroId") int heroId) {
        int userId = userService.loadUserIdByUsername(principal.getName());
        postService.subscribeToHero(userId, heroId);
    }

    @DeleteMapping(path = "/subscriptions/{heroId}")
    public void unsubscribeFromHero(Principal principal, @PathVariable(value = "heroId") int heroId) {
        int userId = userService.loadUserIdByUsername(principal.getName());
        postService.unsubscribeFromHero(userId, heroId);
    }

    @GetMapping(path = "/hero/{heroId}")
    public List<Post> getHeroPosts(@PathVariable(value = "heroId") int heroId, Pageable pageable) {
        return postService.getHeroPosts(heroId, pageable);
    }

    @PostMapping
    public Post createPost(Principal principal, @Valid @RequestBody PostTO postTo) {
        return postService.createPost(principal.getName(), postTo);
    }
}
