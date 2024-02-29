package com.theboys.data.repos;

import com.theboys.data.entities.Vacancy;
import com.theboys.data.enums.VacancyStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, Integer> {

    @Transactional
    @Modifying
    @Query("update Vacancy v set v.status = ?1 where v.vacancyId = ?2")
    void  updateCandidateRequestStatus(VacancyStatus status, Integer vacancyId);
}
