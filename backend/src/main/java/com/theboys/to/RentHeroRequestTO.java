package com.theboys.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentHeroRequestTO {
    private Integer heroId;
    private String heroDescription;
    private String requestDescription;
    private LocalDate dateStart;
    private LocalDate dateEnd;
}
