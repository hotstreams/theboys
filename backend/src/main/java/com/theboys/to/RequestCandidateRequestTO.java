package com.theboys.to;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestCandidateRequestTO {

    private LocalDate birthday;

    private String sex;

    private String race;

    private Integer weight;

    private Integer height;

    private String description;
}
