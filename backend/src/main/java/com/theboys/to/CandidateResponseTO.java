package com.theboys.to;


import com.theboys.data.enums.CandidateStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponseTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String sex;
    private String race;
    private Integer height;
    private Integer weight;
    private String address;
    private String photo;
    private CandidateStatus status;
}
