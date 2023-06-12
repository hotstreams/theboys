package com.theboys.data.repos;

import com.theboys.data.entities.CandidateRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRequestRepo extends CrudRepository<CandidateRequest, Integer> {
}
