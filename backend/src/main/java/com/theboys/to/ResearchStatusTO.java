package com.theboys.to;

import com.theboys.data.enums.ResearchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearchStatusTO {
    private ResearchStatus status;
}
