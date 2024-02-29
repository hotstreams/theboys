package com.theboys.to;

import com.theboys.data.enums.CandidateRequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RequestCandidateResponseTO {
    private String id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String sex;
    private String race;
    private String height;
    private String weight;
    private String description;
    private String scientistId;
    private CandidateRequestStatus status;
}
