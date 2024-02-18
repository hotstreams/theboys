package com.theboys.data.repos;

import com.theboys.data.entities.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findPostsByHeroId(Integer heroId, Pageable pageable);

    @Query(countQuery = "select count(*) from theboys.posts where hero_id in (select hero_id from theboys.user_hero_subscriptions where user_id=:userId) order by \"at\" desc;",
            value = "select * from theboys.posts where hero_id in (select hero_id from theboys.user_hero_subscriptions where user_id=:userId) order by \"at\" desc;",
            nativeQuery = true)
    List<Post> findPostsByUserSubscriptions(@Param("userId")Integer userId, Pageable pageable);
}
