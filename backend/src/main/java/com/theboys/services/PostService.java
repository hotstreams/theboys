package com.theboys.services;

import com.theboys.data.entities.Hero;
import com.theboys.data.entities.Post;
import com.theboys.data.entities.Subscription;
import com.theboys.data.entities.SubscriptionId;
import com.theboys.data.repos.HeroRepo;
import com.theboys.data.repos.PostRepo;
import com.theboys.data.repos.SubscriptionsRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.to.PostTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepo postRepo;

    private final PersistentUserManager userService;

    private final HeroRepo heroRepo;

    private final SubscriptionsRepo subscriptionsRepo;

    @Autowired
    public PostService(PostRepo postRepo,
                       PersistentUserManager userService,
                       HeroRepo heroRepo,
                       SubscriptionsRepo subscriptionsRepo) {
        this.postRepo = postRepo;
        this.userService = userService;
        this.heroRepo = heroRepo;
        this.subscriptionsRepo = subscriptionsRepo;
    }

    public List<PostTO> getHeroPosts(int heroId, Pageable pageable) {
        return postRepo.findPostsByHeroId(heroId, pageable).stream()
                .map(this::createPostTO)
                .collect(Collectors.toList());
    }

    public List<PostTO> getPostsBySubscription(int userId, Pageable pageable) {
        return postRepo.findPostsByUserSubscriptions(userId, pageable).stream()
                .map(this::createPostTO)
                .collect(Collectors.toList());
    }

    public PostTO createPost(String username, PostTO postTo) {
        int userId = userService.loadUserIdByUsername(username);
        Post post = new Post();
        post.setHeroId(userId);
        post.setTitle(postTo.getTitle());
        post.setAt(OffsetDateTime.now());
        post.setDescription(postTo.getDescription());
        post = postRepo.save(post);
        return createPostTO(post);
    }

    public void subscribeToHero(int userId, int heroId) {
        Subscription subscription = new Subscription();
        subscription.setHeroId(heroId);
        subscription.setUserId(userId);
        subscriptionsRepo.save(subscription);
    }

    public void unsubscribeFromHero(int userId, int heroId) {
        SubscriptionId subscriptionId = new SubscriptionId();
        subscriptionId.setHeroId(heroId);
        subscriptionId.setUserId(userId);
        subscriptionsRepo.deleteById(subscriptionId);
    }

    private PostTO createPostTO(Post post) {
        Optional<Hero> optionalHero = heroRepo.findById(post.getHeroId());

        PostTO postTO = new PostTO();
        postTO.setDescription(post.getDescription());
        postTO.setAt(post.getAt());
        optionalHero.map(Hero::getName).ifPresent(postTO::setHeroName);
        postTO.setTitle(post.getTitle());
        return postTO;
    }
}
