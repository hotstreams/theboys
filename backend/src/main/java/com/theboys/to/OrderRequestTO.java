package com.theboys.to;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestTO {
    private Integer heroId;
    private String heroDescription;
    private String requestDescription;
    @NotNull
    private LocalDate dateStart;
    @NotNull
    private LocalDate dateEnd;
}
