package com.theboys.to;

import com.theboys.data.entities.Assessment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResponseTO {
    private Integer id;
    private String name;
    private boolean passed;
    private LocalDate requestDate;
    private LocalDate eventDate;
    private String comment;

    public AssessmentResponseTO(Assessment assessment) {
        this.id = assessment.getId();
        this.name = assessment.getName();
        this.passed = assessment.isPassed();
        this.requestDate = assessment.getRequestDate();
        this.eventDate = assessment.getEventDate();
        this.comment = assessment.getComment();
    }
}
