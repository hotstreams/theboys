package com.theboys.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateRequestTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String dateOfBirth;
    private String sex;
    private String race;
    private String height;
    private String weight;
    private String address;
    private String description;
    private String photo;
    private String medicalDoc;
}
