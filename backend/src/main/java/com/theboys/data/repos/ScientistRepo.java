package com.theboys.data.repos;

import com.theboys.data.entities.Scientist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScientistRepo extends CrudRepository<Scientist, Integer> {
}
