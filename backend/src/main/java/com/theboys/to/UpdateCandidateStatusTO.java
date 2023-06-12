package com.theboys.to;

import com.theboys.data.enums.CandidateStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCandidateStatusTO {
    private CandidateStatus status;
}
