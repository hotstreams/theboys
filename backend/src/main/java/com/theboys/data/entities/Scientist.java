package com.theboys.data.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "scientists", schema = "theboys")
public class Scientist {

    @Id
    @Column(name = "scientist_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer scientistId;

    @OneToMany(mappedBy = "scientist")
    private List<HeroCreationEvent> heroCreationEvents;

    @OneToMany(mappedBy = "scientist")
    private List<CandidateRequest> candidateRequests;

    @OneToMany(mappedBy = "scientist")
    private List<Research> researches;

    public Integer getScientistId() {
        return scientistId;
    }

    public void setScientistId(Integer scientistId) {
        this.scientistId = scientistId;
    }

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
