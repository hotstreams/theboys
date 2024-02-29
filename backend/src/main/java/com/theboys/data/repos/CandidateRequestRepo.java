package com.theboys.data.repos;

import com.theboys.data.entities.CandidateRequest;
import com.theboys.data.enums.CandidateRequestStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRequestRepo extends JpaRepository<CandidateRequest, Integer> {

    List<CandidateRequest> findByScientistScientistId(Integer scientistId);

    @Transactional
    @Modifying
    @Query("update CandidateRequest c set c.status = ?1 where c.candidateId = ?2")
    void  updateCandidateRequestStatus(CandidateRequestStatus status, Integer candidateRequestId);
}
