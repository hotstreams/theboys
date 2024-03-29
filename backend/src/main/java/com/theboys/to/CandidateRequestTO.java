package com.theboys.to;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
