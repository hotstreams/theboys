package com.theboys.data.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "assessments", schema = "theboys")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "test_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "passed")
    private boolean passed;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @ManyToOne(targetEntity = Research.class)
    @JoinColumn(name = "research_id", nullable = false, referencedColumnName = "research_id")
    private Research research;

    @Column(name = "comment")
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
