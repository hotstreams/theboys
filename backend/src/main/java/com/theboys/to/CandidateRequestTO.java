package com.theboys.to;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateRequestTO {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String dateOfBirth;
    @NotEmpty
    private String sex;
    @NotEmpty
    private String race;
    @NotEmpty
    private String height;
    @NotEmpty
    private String weight;
    @NotEmpty
    private String address;
    private String description;
    private String photo;
    private String medicalDoc;
}
