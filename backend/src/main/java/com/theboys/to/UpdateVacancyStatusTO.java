package com.theboys.to;

import com.theboys.data.enums.VacancyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateVacancyStatusTO {

    @NotNull
    private VacancyStatus vacancyStatus;
}
