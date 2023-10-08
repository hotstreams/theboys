package com.theboys.data.entities;

import com.theboys.data.enums.ResearchStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "researches")
public class Research {

    @Id
    @Column(name = "research_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer researchId;

    @ManyToOne
    @JoinColumn(name = "scientist_id", nullable = false)
    private Scientist scientist;

    @Column(name = "result")
    private String result;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Column(name = "doze_ml", nullable = false)
    private Integer dozeMl;

    @Column(name = "doze_count", nullable = false)
    private Integer dozeCount;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ResearchStatus status;

    @OneToMany(mappedBy = "research")
    private List<Test> tests;

    @OneToMany(mappedBy = "research")
    private List<Assessment> assessments;

    @Column(name = "exam_passed")
    private boolean examPassed;

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

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Integer getDozeMl() {
        return dozeMl;
    }

    public void setDozeMl(Integer dozeMl) {
        this.dozeMl = dozeMl;
    }

    public Integer getDozeCount() {
        return dozeCount;
    }

    public void setDozeCount(Integer dozeCount) {
        this.dozeCount = dozeCount;
    }

    public ResearchStatus getStatus() {
        return status;
    }

    public void setStatus(ResearchStatus status) {
        this.status = status;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public boolean isExamPassed() {
        return examPassed;
    }

    public void setExamPassed(boolean examPassed) {
        this.examPassed = examPassed;
    }
}
