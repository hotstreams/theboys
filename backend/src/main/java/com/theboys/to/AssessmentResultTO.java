package com.theboys.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResultTO {
    private boolean passed;
    private String comment;
    private String eventDate;
}
