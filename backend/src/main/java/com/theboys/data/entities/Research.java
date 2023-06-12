package com.theboys.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "researches")
public class Research {

    @Id
    @Column(name = "research_id")
    private Integer researchId;

    @ManyToOne
    @JoinColumn(name = "scientist_id")
    private Scientist scientist;

    private String result;

    public Integer getResearchId() {
        return researchId;
    }

    public void setResearchId(Integer researchId) {
        this.researchId = researchId;
    }

    public Scientist getScientist() {
        return scientist;
    }

    public void setScientist(Scientist scientist) {
        this.scientist = scientist;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
