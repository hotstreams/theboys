package com.theboys.data.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "test_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "result")
    private String result;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @ManyToOne(targetEntity = Research.class)
    @JoinColumn(name = "research_id", nullable = false, referencedColumnName = "research_id")
    private Research research;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}
