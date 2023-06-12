package com.theboys.data.repos;

import com.theboys.data.entities.HeroCreationOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroCreationOrderRepo extends CrudRepository<HeroCreationOrder, Integer> {
}
