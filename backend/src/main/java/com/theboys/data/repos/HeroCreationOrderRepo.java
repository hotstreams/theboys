package com.theboys.data.repos;

import com.theboys.data.entities.HeroCreationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroCreationOrderRepo extends JpaRepository<HeroCreationOrder, Integer> {
}
