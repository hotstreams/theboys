package com.theboys.data.repos;

import com.theboys.data.entities.RateId;
import com.theboys.data.entities.UserHeroRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHeroRateRepo extends JpaRepository<UserHeroRate, RateId> {
}
