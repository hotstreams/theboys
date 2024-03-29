package com.theboys.data.repos;

import com.theboys.data.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "posts", path = "posts")
public interface PostRepo extends JpaRepository<Post, Integer> {
    @RestResource
    List<Post> findPostsByHeroId(@Param("heroId") Integer heroId);
}
