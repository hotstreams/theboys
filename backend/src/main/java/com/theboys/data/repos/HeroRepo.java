package com.theboys.data.repos;

import com.theboys.data.entities.Hero;
import com.theboys.data.entities.HeroToRating;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepo extends JpaRepository<Hero, Integer> {
    @Query(value = "select new com.theboys.data.entities.HeroToRating(r.heroId, avg(r.rate)) from UserHeroRate r group by heroId")
    List<HeroToRating> getHeroRates();

    @Transactional
    @Modifying
    @Query(value = "insert into theboys.user_hero_rates values(:heroId, :userId, :rate) on conflict (hero_id, user_id) do update set hero_id = excluded.hero_id, user_id = excluded.user_id, rate = excluded.rate;", nativeQuery = true)
    void rateHero(@Param("heroId") int heroId, @Param("userId") int userId, @Param("rate") int rate);
}
