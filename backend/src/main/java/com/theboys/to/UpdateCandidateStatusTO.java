package com.theboys.to;

import com.theboys.data.enums.CandidateStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCandidateStatusTO {
    @NotNull
    private CandidateStatus status;
}
