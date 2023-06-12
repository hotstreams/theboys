package com.theboys.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearchRequestTO {
    private Integer candidateId;
    private Integer medicineId;
    private Integer dozeMl;
    private Integer dozeCount;
}
