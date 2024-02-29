package com.theboys.to;

import com.theboys.data.enums.CandidateStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCandidateStatusTO {
    @NotNull
    private CandidateStatus status;
}
