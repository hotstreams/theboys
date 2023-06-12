package com.theboys.data.repos;

import com.theboys.data.entities.Research;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchRepo extends CrudRepository<Research, Integer> {
}
