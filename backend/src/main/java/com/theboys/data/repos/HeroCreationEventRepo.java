package com.theboys.data.repos;

import com.theboys.data.entities.HeroCreationEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroCreationEventRepo extends CrudRepository<HeroCreationEvent, Integer> {
}
