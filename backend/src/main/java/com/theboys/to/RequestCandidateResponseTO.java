package com.theboys.to;

import com.theboys.data.enums.CandidateRequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RequestCandidateResponseTO {
    private Integer id;
    private String dateOfBirth;
    private String sex;
    private String race;
    private Integer height;
    private Integer weight;
    private String description;
    private Integer scientistId;
    private CandidateRequestStatus status;
}
