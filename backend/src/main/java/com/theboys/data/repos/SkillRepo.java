package com.theboys.data.repos;

import com.theboys.data.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Integer> {
    Set<Skill> findByNameIn(Collection<String> names);
}
