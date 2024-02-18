package com.theboys.data.repos;

import com.theboys.data.entities.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepo extends JpaRepository<Hero, Integer> {
    @Query(value = "select hero_id, AVG(rate) from theboys.user_hero_rates group by hero_id;", nativeQuery = true)
    List<Hero> getHeroRates();
}
