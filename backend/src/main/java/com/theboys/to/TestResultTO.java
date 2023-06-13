package com.theboys.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResultTO {
    private LocalDate eventDate;
    private String testResult;
}
