package com.theboys.data.repos;

import com.theboys.data.entities.Research;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchRepo extends JpaRepository<Research, Integer> {
}
