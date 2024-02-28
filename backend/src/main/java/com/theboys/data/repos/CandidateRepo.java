package com.theboys.data.repos;

import com.theboys.data.entities.Candidate;
import com.theboys.data.enums.CandidateStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Integer> {

    @Transactional
    @Modifying
    @Query("update Candidate c set c.candidateStatus = ?1 where c.candidateId = ?2")
    void updateCandidateStatus(CandidateStatus status, Integer candidateId);
}
