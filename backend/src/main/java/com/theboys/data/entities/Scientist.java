package com.theboys.data.entities;

import com.theboys.security.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "scientists")
@PrimaryKeyJoinColumn(name = "scientist_id")
public class Scientist  extends User {

    @OneToMany(mappedBy = "scientist")
    private List<HeroCreationEvent> heroCreationEvents;

    @OneToMany(mappedBy = "scientist")
    private List<CandidateRequest> candidateRequests;

    @OneToMany(mappedBy = "scientist")
    private List<Research> researches;

    public List<HeroCreationEvent> getHeroCreationEvents() {
        return heroCreationEvents;
    }

    public void setHeroCreationEvents(List<HeroCreationEvent> heroCreationEvents) {
        this.heroCreationEvents = heroCreationEvents;
    }

    public List<CandidateRequest> getCandidateRequests() {
        return candidateRequests;
    }

    public void setCandidateRequests(List<CandidateRequest> candidateRequests) {
        this.candidateRequests = candidateRequests;
    }

    public List<Research> getResearches() {
        return researches;
    }

    public void setResearches(List<Research> researches) {
        this.researches = researches;
    }
}
