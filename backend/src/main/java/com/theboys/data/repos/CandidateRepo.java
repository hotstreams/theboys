package com.theboys.data.repos;

import com.theboys.data.entities.Candidate;
import com.theboys.data.enums.CandidateStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Integer> {

    @Transactional
    @Modifying
    @Query("update Candidate c set c.candidateStatus = ?1 where c.id = ?2")
    void updateCandidateStatus(CandidateStatus status, Integer candidateId);

    @Transactional
    @Modifying
    @Query(value = "insert into S265062.candidates (first_name, last_name, phone, birthday, sex, race, " +
            "weight, height, address, description, photo, medical_doc, candidate_id) values (:firstName, :lastName, " +
            ":phone, :birthday, :sex, :race, :weight, :height, :address, :description, :photo, :medicalDoc, :userId)",
            nativeQuery = true)
    void saveCandidate(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phone") String phone,
                       @Param("birthday") LocalDate birthday, @Param("sex") String sex, @Param("race") String race,
                       @Param("weight") int weight, @Param("height") int height, @Param("address") String addres,
                       @Param("description") String description, @Param("photo") byte[] photo, @Param("medicalDoc") byte[] medicalDocument,
                       @Param("userId") int userId);
}
