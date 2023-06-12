package com.theboys.data.repos;

import com.theboys.data.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine, Integer> {
}
