package com.theboys.to;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
public class RequestCandidateRequestTO {

    private String firstName = "";

    private String lastName = "";

    private String birthday = "";

    private String sex = "";

    private String race = "";

    private String weight = "";

    private String height = "";

    private String description = "";
}
