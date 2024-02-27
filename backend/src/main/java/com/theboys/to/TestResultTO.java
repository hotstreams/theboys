package com.theboys.to;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResultTO {
    private LocalDate eventDate;
    private String testResult;
}
