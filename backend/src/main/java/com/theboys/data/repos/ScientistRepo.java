package com.theboys.data.repos;

import com.theboys.data.entities.Scientist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScientistRepo extends JpaRepository<Scientist, Integer> {
}
