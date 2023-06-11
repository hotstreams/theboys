package com.theboys.data.repos;

import com.theboys.data.entities.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "heroes")
public interface HeroRepo extends CrudRepository<Hero, Integer> {
}
