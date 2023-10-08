package com.theboys.data.repos;

import com.theboys.data.entities.HeroCreationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroCreationEventRepo extends JpaRepository<HeroCreationEvent, Integer> {
}
