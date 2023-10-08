package com.theboys.to;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearchRequestTO {
    @NotNull
    private Integer candidateId;
    @NotNull
    private Integer medicineId;
    @NotNull
    private Integer dozeMl;
    @NotNull
    private Integer dozeCount;
}
