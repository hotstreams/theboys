package com.theboys.to;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
