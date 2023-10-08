package com.theboys.to;

import com.theboys.data.entities.*;
import com.theboys.data.enums.ResearchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearchResponseTO {
    private Integer researchId;
    private String result;
    private Candidate candidate;
    private String medicine;
    private Integer dozeMl;
    private Integer dozeCount;
    private ResearchStatus status;
    private List<TestResponseTO> tests;
    private List<AssessmentResponseTO> assessments;
    private boolean examPassed;

    public ResearchResponseTO(Research research) {
        this.researchId = research.getResearchId();
        this.result = research.getResult();
        this.candidate = research.getCandidate();
        this.medicine = research.getMedicine().getName();
        this.dozeMl = research.getDozeMl();
        this.dozeCount = research.getDozeCount();
        this.status = research.getStatus();
        this.examPassed = research.isExamPassed();

        if (research.getTests() != null) {
            this.tests = research.getTests().stream().map(TestResponseTO::new).collect(Collectors.toList());
        }
        if (research.getAssessments() != null) {
            this.assessments = research.getAssessments().stream().map(AssessmentResponseTO::new).collect(Collectors.toList());
        }
    }
}
