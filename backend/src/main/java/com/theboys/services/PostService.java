package com.theboys.services;

import com.theboys.data.entities.Post;
import com.theboys.data.repos.PostRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.to.PostTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepo postRepo;

    private final PersistentUserManager userService;

    @Autowired
    public PostService(PostRepo postRepo, PersistentUserManager userService) {
        this.postRepo = postRepo;
        this.userService = userService;
    }

    public List<Post> getHeroPosts(int heroId, Pageable pageable) {
        return postRepo.findPostsByHeroId(heroId, pageable);
    }

    public List<Post> getPostsBySubscription(int userId, Pageable pageable) {
        return postRepo.findPostsByUserSubscriptions(userId, pageable);
    }

    public Post createPost(String username, PostTO postTo) {
        int userId = userService.loadUserIdByUsername(username);
        Post post = new Post();
        post.setHeroId(userId);
        post.setTitle(postTo.getTitle());
        post.setAt(OffsetDateTime.now());
        post.setDescription(postTo.getDescription());
        return postRepo.save(post);
    }

    public void subscribeToHero(int userId, int heroId) {
        postRepo.saveUserHeroSubscription(userId, heroId);
    }

    public void unsubscribeFromHero(int userId, int heroId) {
        postRepo.deleteUserHeroSubscription(userId, heroId);
    }
}
