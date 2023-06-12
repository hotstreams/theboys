package com.theboys.data.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "hero_creation_events", schema = "theboys")
public class HeroCreationEvent {

    @Id
    @Column(name = "hero_creation_event_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer heroCreationEventId;

    @ManyToOne
    @JoinColumn(name = "scientist_id")
    private Scientist scientist;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "result", nullable = false)
    private String result;

    public HeroCreationEvent() {
    }

    public Integer getHeroCreationEventId() {
        return heroCreationEventId;
    }

    public void setHeroCreationEventId(Integer heroCreationEventId) {
        this.heroCreationEventId = heroCreationEventId;
    }

    public Scientist getScientist() {
        return scientist;
    }

    public void setScientist(Scientist scientist) {
        this.scientist = scientist;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
