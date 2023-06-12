package com.theboys.data.repos;

import com.theboys.data.entities.Candidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepo extends CrudRepository<Candidate, Integer> {
}
