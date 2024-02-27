package com.theboys.to;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResultTO {
    private boolean passed;
    private String comment;
    private String eventDate;
}
