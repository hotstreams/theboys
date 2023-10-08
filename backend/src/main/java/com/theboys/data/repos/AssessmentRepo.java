package com.theboys.data.repos;

import com.theboys.data.entities.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepo  extends JpaRepository<Assessment, Integer> {
}
