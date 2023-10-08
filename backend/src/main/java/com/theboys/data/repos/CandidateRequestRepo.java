package com.theboys.data.repos;

import com.theboys.data.entities.CandidateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRequestRepo extends JpaRepository<CandidateRequest, Integer> {
}
