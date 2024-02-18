package com.theboys.services;

import com.theboys.data.entities.Post;
import com.theboys.data.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepo postRepo;

    @Autowired
    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public List<Post> getHeroPosts(int heroId, Pageable pageable) {
        return postRepo.findPostsByHeroId(heroId, pageable);
    }

    public List<Post> getPostsBySubscription(int userId, Pageable pageable) {
        return postRepo.findPostsByUserSubscriptions(userId, pageable);
    }

    public void subscribeToHero(int userId, int heroId) {
        postRepo.saveUserHeroSubscription(userId, heroId);
    }

    public void unsubscribeFromHero(int userId, int heroId) {
        postRepo.deleteUserHeroSubscription(userId, heroId);
    }
}
