package com.theboys.data.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "candidate_requests", schema = "theboys")
public class CandidateRequest {

    @Id
    @Column(name = "candidate_order_id")
    private Integer candidateId;

    @ManyToOne
    @JoinColumn(name = "scientist_id")
    private Scientist scientist;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "sex")
    private String sex;

    @Column(name = "race")
    private String race;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "height", nullable = false)
    private int height;

    @Column(name = "description")
    private String description;

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Scientist getScientist() {
        return scientist;
    }

    public void setScientist(Scientist scientist) {
        this.scientist = scientist;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
